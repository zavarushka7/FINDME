package com.bignerdranch.android.findme
//Активити, в котором админ выбирает имя и аватарку.
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SettingsProfile : AppCompatActivity() {

    private lateinit var horseButton: ImageButton
    private lateinit var horse2Button: ImageView
    private lateinit var bearButton: ImageButton
    private lateinit var bear2Button: ImageView
    private lateinit var rabbitButton: ImageButton
    private lateinit var rabbit2Button: ImageView
    private lateinit var sheepButton: ImageButton
    private lateinit var sheep2Button: ImageView
    private lateinit var owlButton: ImageButton
    private lateinit var owl2Button: ImageView
    private lateinit var birdButton: ImageButton
    private lateinit var bird2Button: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: ImageButton
    private var selectedAvatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_profile)

        nameEditText = findViewById(R.id.editTextText)
        saveButton = findViewById(R.id.save_button)
        horseButton = findViewById(R.id.horse_button)
        horse2Button = findViewById(R.id.horse2_button)
        bearButton = findViewById(R.id.bear_button)
        bear2Button = findViewById(R.id.bear2_button)
        rabbitButton = findViewById(R.id.rabbit_button)
        rabbit2Button = findViewById(R.id.rabbit2_button)
        sheepButton = findViewById(R.id.sheep_button)
        sheep2Button = findViewById(R.id.sheep2_button)
        owlButton = findViewById(R.id.owl_button)
        owl2Button = findViewById(R.id.owl2_button)
        birdButton = findViewById(R.id.bird_button)
        bird2Button = findViewById(R.id.bird2_button)

        database = Firebase.database.reference
        val imageButtons = listOf<ImageButton>(birdButton, horseButton, rabbitButton, sheepButton, owlButton, bearButton)
        val imageViews = listOf<View>(bird2Button, horse2Button, rabbit2Button, sheep2Button, owl2Button, bear2Button)
        var selectedImageView: View? = null

        for (i in imageButtons.indices) {
            val imageButton = imageButtons[i]
            val imageView = imageViews[i]

            imageButton.setOnClickListener {
                if (selectedImageView == imageView) {
                    // Если уже выбран этот же imageView, скрываем его
                    imageView.visibility = View.GONE
                    selectedImageView = null
                } else {
                    // Скрываем все imageView
                    for (view in imageViews) {
                        view.visibility = View.GONE
                    }
                    // Показываем новый выбранный imageView
                    imageView.visibility = View.VISIBLE
                    selectedImageView = imageView
                    selectedAvatar = imageView.toString()

                }
            }
        }

        saveButton.setOnClickListener {
            val userName = nameEditText.text.toString()

            // Проверяем, что введено имя пользователя
            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else if (selectedAvatar == null) {
                Toast.makeText(this, "Please select an avatar", Toast.LENGTH_SHORT).show()
            } else {
                // Получение уникального ключа узла
                val newDatabaseReference = database.child("users").push()
                val key = newDatabaseReference.key

                // Создание объекта пользователя и добавление информации в базу данных Firebase
                val user = User(key.toString(), userName, selectedAvatar!!, "admin","","","","",""
                    ,"","","","","","","","","","","","","","",""
                    ,"","","","","", 0)
                if (key != null) {
                    database.child("users").child(key).setValue(user)
                }

                // Запуск активити GetCode с передачей уникального ключа в Intent
                val intent = Intent(this@SettingsProfile, GetCode::class.java)
                intent.putExtra("key", key)

                startActivity(intent)
            }
        }

    }
    data class User(val key: String, val name: String, val avatar: String, val status: String, val a11: String,
                    val a12: String, val a13: String, val a14: String, val a15: String, val a21: String,
                    val a22: String, val a23: String, val a24: String, val a25: String, val a31: String,
                    val a32: String, val a33: String, val a34: String, val a35: String, val a41: String,
                    val a42: String, val a43: String, val a44: String, val a45: String, val a51: String,
                    val a52: String, val a53: String, val a54: String, val a55: String, val scores: Int)


}
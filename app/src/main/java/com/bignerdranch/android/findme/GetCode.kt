package com.bignerdranch.android.findme
// Активити, в котором админом выбирается режим и создается игра.
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class GetCode : AppCompatActivity() {
    private var randomNumber: Int = 0
    private lateinit var randomNumberTextView: TextView
    private lateinit var friendsButton: ImageButton
    private lateinit var friendsButton2: ImageView
    private lateinit var strangersButton: ImageButton
    private lateinit var strangersButton2: ImageView
    private lateinit var startButton2: ImageButton
    private lateinit var database: DatabaseReference
    private var gameReference: DatabaseReference? = null
    var mode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_code)

        friendsButton = findViewById(R.id.friends)
        friendsButton2 = findViewById(R.id.friends2)
        strangersButton = findViewById(R.id.strangers)
        strangersButton2 = findViewById(R.id.strangers2)
        startButton2 = findViewById(R.id.startButton2)
        randomNumberTextView = findViewById(R.id.randomNumberTextView)
        database = Firebase.database.reference
        val imageButtons = listOf<ImageButton>(friendsButton, strangersButton)
        val imageViews = listOf<View>(friendsButton2, strangersButton2)
        var selectedImageView: View? = null
        for (i in imageButtons.indices) {
            val imageButton = imageButtons[i]
            val imageView = imageViews[i]

            imageButton.setOnClickListener {
                if (selectedImageView == imageView) {
                    imageView.visibility = View.GONE
                    selectedImageView = null
                    mode = ""
                } else {
                    // Скрываем все imageView
                    for (view in imageViews) {
                        view.visibility = View.GONE
                    }

                    imageView.visibility = View.VISIBLE
                    selectedImageView = imageView
                    mode = resources.getResourceEntryName(imageView.id)
                }
            }
        }
        startButton2.setOnClickListener {
            val admin = intent.getStringExtra("key")
            if (selectedImageView == null) {
                Toast.makeText(this, "Please select an avatar", Toast.LENGTH_SHORT).show()
            } else {
                val gameInfo = GameInfo(admin.toString(), mode, randomNumber, 1)
                gameReference = database.child("games").child(admin.toString())
                val gameKey = gameReference?.key

                if (gameKey != null) {
                    database.child("game").child(gameKey).setValue(gameInfo)
                }
                val intent = Intent(this@GetCode, Wait::class.java)
                startActivity(intent)
            }
        }
        // Восстановление случайного числа при изменении конфигурации
        if (savedInstanceState != null) {
            randomNumber = savedInstanceState.getInt("randomNumber")
        } else {
            // Генерируем случайное число
            generateRandomNumber()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("randomNumber", randomNumber)
    }
    private fun generateRandomNumber() {
        randomNumber = (1000..9999).random()
        randomNumberTextView.text = randomNumber.toString()
    }
    data class GameInfo(val admin: String, val mode: String, val code: Int, val count: Int)
}
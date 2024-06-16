package com.bignerdranch.android.findme
// Активити, в котором игрок подключается к созданной игре.
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class Connect : AppCompatActivity() {
    private lateinit var code: EditText
    private lateinit var connectButton: ImageButton
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    var gameKey: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        connectButton = findViewById(R.id.connect_button)
        code = findViewById(R.id.randomcode)
        database = FirebaseDatabase.getInstance().reference.child("game")
        database2 = FirebaseDatabase.getInstance().reference.child("users")
        connectButton.setOnClickListener {
            val userInput = code.text.toString()
            var codeMatched = false
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (childSnapshot in dataSnapshot.children) {
                        val gameCode = childSnapshot.child("code").getValue(Int::class.java)
                        if (gameCode != null && gameCode == userInput.toInt()) {
                            codeMatched = true
                            gameKey = childSnapshot.key
                            break
                        }
                    }
                    if (codeMatched) {
                        gameKey?.let { key ->

                            val key_player = intent.getStringExtra("key")
                            val playersRef = database.child(key).child("players")
                            val playerCount = dataSnapshot.child(key).child("count").getValue(Int::class.java) ?: 0
                            val playerData = hashMapOf<String, Any>()
                            playerData["name"] = key_player.toString()
                            playersRef.child(key_player.toString()).setValue(playerData)

                            val countRef = database.child(key).child("count")
                            countRef.setValue(playerCount + 1)

                            val intent = Intent(this@Connect, Wait2::class.java)
                            intent.putExtra("GameCode", key)
                            intent.putExtra("key_pl", key_player)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@Connect, "Codes do not match!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибки
                }
            })
        }
    }
}
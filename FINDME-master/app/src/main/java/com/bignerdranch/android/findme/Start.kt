package com.bignerdranch.android.findme
// Активити показывает сводку правил о режимах игры
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class Start : AppCompatActivity() {

    private lateinit var friendsButton: ImageButton
    private lateinit var strangersButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        friendsButton = findViewById(R.id.friends)
        strangersButton = findViewById(R.id.strangers)

        friendsButton.setOnClickListener { view: View ->
            val intent = Intent(this, GetCode::class.java)
            startActivity(intent)

        }
        strangersButton.setOnClickListener { view: View ->
            val intent = Intent(this, GetCode::class.java)
            startActivity(intent)
        }
    }
}
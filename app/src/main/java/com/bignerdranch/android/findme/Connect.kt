package com.bignerdranch.android.findme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Connect : AppCompatActivity() {

    private lateinit var connectButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        val codeEditText: EditText = findViewById(R.id.editText)
        val connectButton: Button = findViewById(R.id.connect_button)
        val receivedRandomNumber = intent.getIntExtra("randomNumber", 0)
        connectButton.setOnClickListener { view: View ->

            val enteredCode = codeEditText.text.toString()
            if (enteredCode == receivedRandomNumber.toString()) {
                val intent = Intent(this, Wait::class.java)
                startActivity(intent)

            } else {
                // Если введенный код неверен
                Toast.makeText(this, "Неправильный код", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
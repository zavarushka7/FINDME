package com.bignerdranch.android.findme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class GetCode : AppCompatActivity() {
    private var randomNumber: Int = 0
    private lateinit var randomNumberTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_code)

        randomNumberTextView = findViewById(R.id.randomNumberTextView)

        if (savedInstanceState != null) {
            randomNumber = savedInstanceState.getInt("randomNumber")
            // Восстановить значение переменной
        } else {
            // Генерируем случайное число
            randomNumber = (1000..9999).random()
            val intent = Intent(this, Connect::class.java)
            intent.putExtra("randomNumber", randomNumber)

        }

        updateRandomNumber()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("randomNumber", randomNumber)
        // Сохранить значение переменной
    }

    private fun updateRandomNumber() {
        randomNumberTextView.text = randomNumber.toString()
    }

}
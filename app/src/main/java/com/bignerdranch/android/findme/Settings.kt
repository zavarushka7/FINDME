package com.bignerdranch.android.findme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class Settings : AppCompatActivity() {

    private lateinit var changeButton: ImageButton
    private lateinit var volumeButton: ImageButton
    private lateinit var animationButton: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        changeButton = findViewById(R.id.change_button)
        volumeButton = findViewById(R.id.volume_button)
        animationButton = findViewById(R.id.animation_button)

        changeButton.setOnClickListener { view: View ->
            val intent = Intent(this, SettingsProfile::class.java)
            startActivity(intent)
        }
        volumeButton.setOnClickListener { view: View ->
            val intent = Intent(this, SettingsSound::class.java)
            startActivity(intent)
        }
        animationButton.setOnClickListener { view: View ->
            val intent = Intent(this, SettingsAnimation::class.java)
            startActivity(intent)
        }
    }
}
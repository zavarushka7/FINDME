package com.bignerdranch.android.findme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class Menu : AppCompatActivity() {
    private lateinit var startButton: ImageButton
    private lateinit var connectButton: ImageButton
    private lateinit var settingsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        startButton = findViewById(R.id.start_button)
        connectButton = findViewById(R.id.connect_button)
        settingsButton = findViewById(R.id.settings_button)

        startButton.setOnClickListener { view: View ->
            val intent = Intent(this, SettingsProfile::class.java)
            startActivity(intent)
        }
        connectButton.setOnClickListener { view: View ->
            val intent = Intent(this, SettingsProfile2::class.java)
            startActivity(intent)
        }
        settingsButton.setOnClickListener { view: View ->
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }
}
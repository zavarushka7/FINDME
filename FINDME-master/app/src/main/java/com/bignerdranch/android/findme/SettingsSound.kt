package com.bignerdranch.android.findme
//Активити в котором можно включить\выключить звуки игры
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsSound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_sound)
    }
}
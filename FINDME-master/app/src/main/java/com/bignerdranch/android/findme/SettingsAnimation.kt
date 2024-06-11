package com.bignerdranch.android.findme
//Анимация которая появляется при самом первом запуске игры и которую можно пересмотреть в настройках
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsAnimation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_animation)
    }
}
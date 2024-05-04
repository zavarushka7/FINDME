package com.bignerdranch.android.findme
// Активити для админа в котором видно сколько игроков подключились к игре и можно ли начать игру (>=4 игроков)
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Wait : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)
    }
}
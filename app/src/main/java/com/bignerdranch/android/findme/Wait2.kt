package com.bignerdranch.android.findme
//Активити для игроков, в котором видно сколько игроков подключились
// + ожидание админа, пока он не нажмет на кнопку "Начать игру" в Wait
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Wait2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait2)
    }
}
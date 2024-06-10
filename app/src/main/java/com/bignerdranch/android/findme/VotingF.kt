package com.bignerdranch.android.findme
//Голосование в режиме "Друзья", в котором игроки угадывают кому могут принадлежать эти 2 ответа.
// Баллы начисляются тебе, если ты угадал правильно кому принадлежит ответ.
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class VotingF : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)
    }
}
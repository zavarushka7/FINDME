package com.bignerdranch.android.findme
// Голосование в режиме "Незнакомцы", в котором игроки выбирают лучший ответ из предложенных двух.
// Баллы делятся в соотношении выбранных и начисляются авторам ответов.
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class VotingS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_s)
    }
}
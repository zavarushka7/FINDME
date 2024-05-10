package com.bignerdranch.android.findme
// Ответы на вопросы
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class AnswersF : AppCompatActivity() {
    private val questions = listOf(
        "Вопрос 1",
        "Вопрос 2",
        "Вопрос 3",
        "Вопрос 4",
        "Вопрос 5",
        "Вопрос 6",
        "Вопрос 7",
        "Вопрос 8",
        "Вопрос 9",
        "Вопрос 10",
        "Вопрос 11",
        "Вопрос 12",
        "Вопрос 13",
        "Вопрос 14",
        "Вопрос 15"

    ).toMutableList()
    private lateinit var answer: EditText
    private lateinit var nextButton: ImageButton
    private lateinit var database: DatabaseReference
    var a11 = ""
    var a12 = ""
    var a13 = ""
    var a14 = ""
    var a15 = ""
    var a21 = ""
    var a22 = ""
    var a23 = ""
    var a24 = ""
    var a25 = ""
    var a31 = ""
    var a32 = ""
    var a33 = ""
    var a34 = ""
    var a35 = ""
    var k = -1
    private var currentQuestion = ""
    private var currentAnswer = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        val ID = intent.getStringExtra("gamecode").toString()
        database = Firebase.database.reference.child("users").child(ID)

        answer = findViewById(R.id.answers)
        nextButton = findViewById(R.id.next_question)

        showNextQuestion()
        nextButton.setOnClickListener {
            val answer = answer.text.toString()
            currentAnswer = answer
            showNextQuestion()
        }


    }
    private fun showNextQuestion() {
        if (questions.isEmpty()) {
            // Все вопросы заданы
            // Можно выполнить какие-то дополнительные действия или перейти на другой экран
        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                0 -> {
                    a11 = a
                    database.child("a11").setValue(a11)
                }

                1 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                2 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                3 -> {
                    a14 = a
                    database.child("a14").setValue(a14)
                }
                4 -> {
                    a15 = a
                    database.child("a15").setValue(a15)
                }
                5 -> {
                    a21 = a
                    database.child("a21").setValue(a21)
                }
                6 -> {
                    a22 = a
                    database.child("a22").setValue(a22)
                }
                7 -> {
                    a23 = a
                    database.child("a23").setValue(a23)
                }
                8 -> {
                    a24 = a
                    database.child("a24").setValue(a24)
                }
                9 -> {
                    a25 = a
                    database.child("a25").setValue(a25)
                }
                10 -> {
                    a31 = a
                    database.child("a31").setValue(a31)
                }
                11 -> {
                    a32 = a
                    database.child("a32").setValue(a32)
                }
                12 -> {
                    a33 = a
                    database.child("a33").setValue(a33)
                }
                13 -> {
                    a34 = a
                    database.child("a34").setValue(a34)
                }
                14 -> {
                    a35 = a
                    database.child("a35").setValue(a35)
                }
            }
            k ++
            answer.text.clear()
            answer.hint = currentQuestion
            questions.remove(currentQuestion)
        }
    }
}

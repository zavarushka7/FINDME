package com.bignerdranch.android.findme
// Ответы на вопросы для админа
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class AnswersAdmin : AppCompatActivity() {
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
    private lateinit var database2: DatabaseReference
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
    var k = 0
    private var r: Int = 0
    private var currentQuestion = ""
    private var currentAnswer = ""
    private lateinit var mode: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers_admin)
        val ID = intent.getStringExtra("gamecode").toString() // код игры для админа

        val count = intent.getStringExtra("count").toString()
        database = Firebase.database.reference.child("users").child(ID)

        answer = findViewById(R.id.answers)
        nextButton = findViewById(R.id.next_question)
        database2 = FirebaseDatabase.getInstance().reference.child("game").child(ID) // путь для админа
        database2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mode1 = dataSnapshot.child("mode").getValue(String::class.java).toString()
                mode = mode1
                val r1 = dataSnapshot.child("ready").getValue(Int::class.java)
                r = r1!!
                Toast.makeText(this@AnswersAdmin, mode, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        if (count == "4"){showNextQuestion4()}
        else if (count == "5"){showNextQuestion5()}
        else if (count == "6"){showNextQuestion6()}
        else if (count == "7"){showNextQuestion7()}
        else if (count == "8"){showNextQuestion8()}
        nextButton.setOnClickListener {
            val answer = answer.text.toString()
            currentAnswer = answer
            when(count) {
                "4" -> showNextQuestion4()
                "5" -> showNextQuestion5()
                "6" -> showNextQuestion6()
                "7" -> showNextQuestion7()
                "8" -> showNextQuestion8()
            }
        }


    }

    private fun showNextQuestion4() {
        if (k==10) {
            r ++
            database2.child("ready").setValue(r)
            if (mode == "friends2"){
                val intent = Intent(this@AnswersAdmin, VotingF::class.java)
                startActivity(intent)
            }
            else if (mode == "strangers2"){
                val intent = Intent(this@AnswersAdmin, VotingS::class.java)
                startActivity(intent)
            }


        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                1 -> {
                    a11 = a
                    database.child("a11").setValue(a11)
                }

                2 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                3 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                4 -> {
                    a21 = a
                    database.child("a21").setValue(a21)
                }
                5 -> {
                    a22 = a
                    database.child("a22").setValue(a22)
                }
                6 -> {
                    a23 = a
                    database.child("a23").setValue(a23)
                }
                7 -> {
                    a31 = a
                    database.child("a31").setValue(a31)
                }
                8 -> {
                    a32 = a
                    database.child("a32").setValue(a32)
                }
            }
            k ++
            answer.text.clear()
            answer.hint = currentQuestion
            questions.remove(currentQuestion)
        }
    }
    private fun showNextQuestion5() {
        if (k==12) {
            r ++
            database2.child("ready").setValue(r)
            if (mode == "friends2"){
                val intent = Intent(this@AnswersAdmin, VotingF::class.java)
                startActivity(intent)
            }
            else if (mode == "strangers2"){
                val intent = Intent(this@AnswersAdmin, VotingS::class.java)
                startActivity(intent)
            }
        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                1 -> {
                    a11 = a
                    database.child("a11").setValue(a11)
                }
                2 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                3 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                4 -> {
                    a21 = a
                    database.child("a21").setValue(a21)
                }
                5 -> {
                    a22 = a
                    database.child("a22").setValue(a22)
                }
                6 -> {
                    a23 = a
                    database.child("a23").setValue(a23)
                }
                7 -> {
                    a31 = a
                    database.child("a31").setValue(a31)
                }
                8 -> {
                    a32 = a
                    database.child("a32").setValue(a32)
                }
                9 -> {
                    a33 = a
                    database.child("a33").setValue(a33)
                }
                10 -> {
                    a34 = a
                    database.child("a34").setValue(a34)
                }
            }
            k ++
            answer.text.clear()
            answer.hint = currentQuestion
            questions.remove(currentQuestion)
        }
    }
    private fun showNextQuestion6() {
        if (k==11) {
            r ++
            database2.child("ready").setValue(r)
            if (mode == "friends2"){
                val intent = Intent(this@AnswersAdmin, VotingF::class.java)
                startActivity(intent)
            }
            else if (mode == "strangers2"){
                val intent = Intent(this@AnswersAdmin, VotingS::class.java)
                startActivity(intent)
            }
        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                1 -> {a11 = a
                    database.child("a11").setValue(a11)
                }
                2 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                3 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                4 -> {
                    a21 = a
                    database.child("a21").setValue(a21)
                }
                5 -> {
                    a22 = a
                    database.child("a22").setValue(a22)
                }
                6 -> {
                    a23 = a
                    database.child("a23").setValue(a23)
                }
                7 -> {
                    a31 = a
                    database.child("a31").setValue(a31)
                }
                8 -> {
                    a32 = a
                    database.child("a32").setValue(a32)
                }
                9 -> {
                    a33 = a
                    database.child("a33").setValue(a33)
                }
            }
            k ++
            answer.text.clear()
            answer.hint = currentQuestion
            questions.remove(currentQuestion)
        }
    }
    private fun showNextQuestion7() {
        if (k==16) {
            r ++
            database2.child("ready").setValue(r)
            if (mode == "friends2"){
                val intent = Intent(this@AnswersAdmin, VotingF::class.java)
                startActivity(intent)
            }
            else if (mode == "strangers2"){
                val intent = Intent(this@AnswersAdmin, VotingS::class.java)
                startActivity(intent)
            }
        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                1 -> {
                    a11 = a
                    database.child("a11").setValue(a11)
                }
                2 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                3 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                4 -> {
                    a14 = a
                    database.child("a14").setValue(a14)
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
    private fun showNextQuestion8() {
        if (k==14) {
            r ++
            database2.child("ready").setValue(r)
            if (mode == "friends2"){
                val intent = Intent(this@AnswersAdmin, VotingF::class.java)
                startActivity(intent)
            }
            else if (mode == "strangers2"){
                val intent = Intent(this@AnswersAdmin, VotingS::class.java)
                startActivity(intent)
            }
        } else {
            val randomIndex = (0 until questions.size).random()
            currentQuestion = questions[randomIndex]
            var question = findViewById<TextView>(R.id.questions)
            question.text = currentQuestion
            val a = answer.text.toString()
            when (k) {
                1 -> {
                    a11 = a
                    database.child("a11").setValue(a11)
                }
                2 -> {
                    a12 = a
                    database.child("a12").setValue(a12)
                }
                3 -> {
                    a13 = a
                    database.child("a13").setValue(a13)
                }
                4 -> {
                    a14 = a
                    database.child("a14").setValue(a14)
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
                    a31 = a
                    database.child("a31").setValue(a31)
                }
                10 -> {
                    a32 = a
                    database.child("a32").setValue(a32)
                }
                11 -> {
                    a33 = a
                    database.child("a33").setValue(a33)
                }
                12 -> {
                    a34 = a
                    database.child("a34").setValue(a34)
                }
            }
            k ++
            answer.text.clear()
            answer.hint = currentQuestion
            questions.remove(currentQuestion)
        }
    }
}
package com.bignerdranch.android.findme
// Ответы на вопросы для админа
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
class AnswersAdmin : AppCompatActivity() {
    private val questions = mutableListOf<String>()
    private lateinit var answer: EditText
    private lateinit var question: TextView
    private lateinit var nextButton: ImageButton
    private lateinit var database: DatabaseReference
    private lateinit var database3: DatabaseReference
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
    var k = 1
    private var r: Int = 0
    private var currentQuestion = ""
    private var currentAnswer = ""
    private lateinit var gameID: String
    private lateinit var player: String
    var questionIndex = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers_admin)
        val ID = intent.getStringExtra("gamecode").toString()
        player = ID
        gameID = ID
        val count = intent.getStringExtra("count").toString()

        // Инициализируем Firebase references
        database = Firebase.database.reference.child("users").child(ID)
        database3 = FirebaseDatabase.getInstance().reference.child("game").child(gameID)
        // Находим элементы UI
        answer = findViewById(R.id.answers)
        nextButton = findViewById(R.id.next_question)
        question = findViewById(R.id.questions) // Находим текстовое поле для вопроса

        // Слушатель для кнопки "Далее"
        if (count == "4") {
            nextButton.setOnClickListener {
                val answerText = answer.text.toString()
                currentAnswer = answerText
                answer.text.clear()
                answer.hint = "Введите ответ"

                // Переход к следующему вопросу
                questionIndex++
                if (questionIndex < questions.size) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                } else {
                    // Все вопросы обработаны - переход на новый экран
                    r++
                    database3.child("ready").setValue(r)
                    val intent = Intent(this@AnswersAdmin, Ready::class.java)
                    intent.putExtra("IDD", gameID)
                    intent.putExtra("player", player)
                    startActivity(intent)
                }
                // Запись ответа в Firebase
                when (k) {
                    1 -> {
                        a11 = answerText
                        database.child("a11").setValue(hashMapOf(currentQuestion to a11))
                    }

                    2 -> {
                        a12 = answerText
                        database.child("a12").setValue(hashMapOf(currentQuestion to a12))
                    }

                    3 -> {
                        a13 = answerText
                        database.child("a13").setValue(hashMapOf(currentQuestion to a13))
                    }

                    4 -> {
                        a21 = answerText
                        database.child("a21").setValue(hashMapOf(currentQuestion to a21))
                    }

                    5 -> {
                        a22 = answerText
                        database.child("a22").setValue(hashMapOf(currentQuestion to a22))
                    }

                    6 -> {
                        a23 = answerText
                        database.child("a23").setValue(hashMapOf(currentQuestion to a23))
                    }

                    7 -> {
                        a31 = answerText
                        database.child("a31").setValue(hashMapOf(currentQuestion to a31))
                    }

                    8 -> {
                        a32 = answerText
                        database.child("a32").setValue(hashMapOf(currentQuestion to a32))
                    }

                }
                k++

                // Очистка поля ввода и установка подсказки

            }
        }
        else if (count =="5"){
            nextButton.setOnClickListener {
                val answerText = answer.text.toString()
                currentAnswer = answerText
                answer.text.clear()
                answer.hint = "Введите ответ"

                // Переход к следующему вопросу
                questionIndex++
                if (questionIndex < questions.size) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                } else {
                    // Все вопросы обработаны - переход на новый экран
                    r++
                    database3.child("ready").setValue(r)
                    val intent = Intent(this@AnswersAdmin, Ready::class.java)
                    intent.putExtra("IDD", gameID)
                    intent.putExtra("player", player)
                    startActivity(intent)
                }
                // Запись ответа в Firebase
                when (k) {
                    1 -> {
                        a11 = answerText
                        database.child("a11").setValue(hashMapOf(currentQuestion to a11))
                    }
                    2 -> {
                        a12 = answerText
                        database.child("a12").setValue(hashMapOf(currentQuestion to a12))
                    }
                    3 -> {
                        a13 = answerText
                        database.child("a13").setValue(hashMapOf(currentQuestion to a13))
                    }
                    4 -> {
                        a21 = answerText
                        database.child("a21").setValue(hashMapOf(currentQuestion to a21))
                    }
                    5 -> {
                        a22 = answerText
                        database.child("a22").setValue(hashMapOf(currentQuestion to a22))
                    }
                    6 -> {
                        a23 = answerText
                        database.child("a23").setValue(hashMapOf(currentQuestion to a23))
                    }
                    7 -> {
                        a31 = answerText
                        database.child("a31").setValue(hashMapOf(currentQuestion to a31))
                    }
                    8 -> {
                        a32 = answerText
                        database.child("a32").setValue(hashMapOf(currentQuestion to a32))
                    }
                    9 -> {
                        a33 = answerText
                        database.child("a33").setValue(hashMapOf(currentQuestion to a33))
                    }
                    10 -> {
                        a34 = answerText
                        database.child("a34").setValue(hashMapOf(currentQuestion to a34))
                    }

                }
                k++

                // Очистка поля ввода и установка подсказки

            }
        }
        else if (count == "6"){
            nextButton.setOnClickListener {
                val answerText = answer.text.toString()
                currentAnswer = answerText
                answer.text.clear()
                answer.hint = "Введите ответ"

                // Переход к следующему вопросу
                questionIndex++
                if (questionIndex < questions.size) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                } else {
                    // Все вопросы обработаны - переход на новый экран
                    r++
                    database3.child("ready").setValue(r)
                    val intent = Intent(this@AnswersAdmin, Ready::class.java)
                    intent.putExtra("IDD", gameID)
                    intent.putExtra("player", player)
                    startActivity(intent)
                }
                // Запись ответа в Firebase
                when (k) {
                    1 -> {a11 = answerText
                        database.child("a11").setValue(hashMapOf(currentQuestion to a11))
                    }
                    2 -> {
                        a12 = answerText
                        database.child("a12").setValue(hashMapOf(currentQuestion to a12))
                    }
                    3 -> {
                        a13 = answerText
                        database.child("a13").setValue(hashMapOf(currentQuestion to a13))
                    }
                    4 -> {
                        a21 = answerText
                        database.child("a21").setValue(hashMapOf(currentQuestion to a21))
                    }
                    5 -> {
                        a22 = answerText
                        database.child("a22").setValue(hashMapOf(currentQuestion to a22))
                    }
                    6 -> {
                        a23 = answerText
                        database.child("a23").setValue(hashMapOf(currentQuestion to a23))
                    }
                    7 -> {
                        a31 = answerText
                        database.child("a31").setValue(hashMapOf(currentQuestion to a31))
                    }
                    8 -> {
                        a32 = answerText
                        database.child("a32").setValue(hashMapOf(currentQuestion to a32))
                    }
                    9 -> {
                        a33 = answerText
                        database.child("a33").setValue(hashMapOf(currentQuestion to a33))
                    }

                }
                k++

                // Очистка поля ввода и установка подсказки

            }
        }
        else if (count == "7"){
            nextButton.setOnClickListener {
                val answerText = answer.text.toString()
                currentAnswer = answerText
                answer.text.clear()
                answer.hint = "Введите ответ"

                // Переход к следующему вопросу
                questionIndex++
                if (questionIndex < questions.size) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                } else {
                    // Все вопросы обработаны - переход на новый экран
                    r++
                    database3.child("ready").setValue(r)
                    val intent = Intent(this@AnswersAdmin, Ready::class.java)
                    intent.putExtra("IDD", gameID)
                    intent.putExtra("player", player)
                    startActivity(intent)
                }
                // Запись ответа в Firebase
                when (k) {
                    1 -> {
                        a11 = answerText
                        database.child("a11").setValue(hashMapOf(currentQuestion to a11))
                    }
                    2 -> {
                        a12 = answerText
                        database.child("a12").setValue(hashMapOf(currentQuestion to a12))
                    }
                    3 -> {
                        a13 = answerText
                        database.child("a13").setValue(hashMapOf(currentQuestion to a13))
                    }
                    4 -> {
                        a14 = answerText
                        database.child("a14").setValue(hashMapOf(currentQuestion to a14))
                    }
                    5 -> {
                        a21 = answerText
                        database.child("a21").setValue(hashMapOf(currentQuestion to a21))
                    }
                    6 -> {
                        a22 = answerText
                        database.child("a22").setValue(hashMapOf(currentQuestion to a22))
                    }
                    7 -> {
                        a23 = answerText
                        database.child("a23").setValue(hashMapOf(currentQuestion to a23))
                    }
                    8 -> {
                        a24 = answerText
                        database.child("a24").setValue(hashMapOf(currentQuestion to a24))
                    }
                    9 -> {
                        a25 = answerText
                        database.child("a25").setValue(hashMapOf(currentQuestion to a25))
                    }
                    10 -> {
                        a31 = answerText
                        database.child("a31").setValue(hashMapOf(currentQuestion to a31))
                    }
                    11 -> {
                        a32 = answerText
                        database.child("a32").setValue(hashMapOf(currentQuestion to a32))
                    }
                    12 -> {
                        a33 = answerText
                        database.child("a33").setValue(hashMapOf(currentQuestion to a33))
                    }
                    13 -> {
                        a34 = answerText
                        database.child("a34").setValue(hashMapOf(currentQuestion to a34))
                    }
                    14 -> {
                        a35 = answerText
                        database.child("a35").setValue(hashMapOf(currentQuestion to a35))
                    }

                }
                k++

                // Очистка поля ввода и установка подсказки

            }
        }
        else if (count == "8"){
            nextButton.setOnClickListener {
                val answerText = answer.text.toString()
                currentAnswer = answerText
                answer.text.clear()
                answer.hint = "Введите ответ"

                // Переход к следующему вопросу
                questionIndex++
                if (questionIndex < questions.size) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                } else {
                    // Все вопросы обработаны - переход на новый экран
                    r++
                    database3.child("ready").setValue(r)
                    val intent = Intent(this@AnswersAdmin, Ready::class.java)
                    intent.putExtra("IDD", gameID)
                    intent.putExtra("player", player)
                    startActivity(intent)
                }
                // Запись ответа в Firebase
                when (k) {
                    1 -> {
                        a11 = answerText
                        database.child("a11").setValue(hashMapOf(currentQuestion to a11))
                    }
                    2 -> {
                        a12 = answerText
                        database.child("a12").setValue(hashMapOf(currentQuestion to a12))
                    }
                    3 -> {
                        a13 = answerText
                        database.child("a13").setValue(hashMapOf(currentQuestion to a13))
                    }
                    4 -> {
                        a14 = answerText
                        database.child("a14").setValue(hashMapOf(currentQuestion to a14))
                    }
                    5 -> {
                        a21 = answerText
                        database.child("a21").setValue(hashMapOf(currentQuestion to a21))
                    }
                    6 -> {
                        a22 = answerText
                        database.child("a22").setValue(hashMapOf(currentQuestion to a22))
                    }
                    7 -> {
                        a23 = answerText
                        database.child("a23").setValue(hashMapOf(currentQuestion to a23))
                    }
                    8 -> {
                        a24 = answerText
                        database.child("a24").setValue(hashMapOf(currentQuestion to a24))
                    }
                    9 -> {
                        a31 = answerText
                        database.child("a31").setValue(hashMapOf(currentQuestion to a31))
                    }
                    10 -> {
                        a32 = answerText
                        database.child("a32").setValue(hashMapOf(currentQuestion to a32))
                    }
                    11 -> {
                        a33 = answerText
                        database.child("a33").setValue(hashMapOf(currentQuestion to a33))
                    }
                    12 -> {
                        a34 = answerText
                        database.child("a34").setValue(hashMapOf(currentQuestion to a34))
                    }

                }
                k++

                // Очистка поля ввода и установка подсказки

            }
        }


        // Загрузка вопросов из Firebase
        database3.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                r = dataSnapshot.child("ready").getValue(Int::class.java)!!

                // Загрузка вопросов
                for (childSnapshot in dataSnapshot.child("questions").children) {
                    val question = childSnapshot.child("val").getValue(String::class.java)
                    Log.d("KIДЩДKI", question.toString())
                    questions.add(question.toString())

                }

                // Отображение первого вопроса
                if (questions.isNotEmpty()) {
                    currentQuestion = questions[questionIndex]
                    question.text = currentQuestion
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to read value.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}
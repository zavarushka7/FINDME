package com.bignerdranch.android.findme
// Голосование в режиме "Незнакомцы", в котором игроки выбирают лучший ответ из предложенных двух.
// Баллы делятся в соотношении выбранных и начисляются авторам ответов.


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class VotingS : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private lateinit var Buttona1: ImageButton
    private lateinit var Buttona2: ImageButton
    private lateinit var a1: String
    private lateinit var a2: String
    private lateinit var textQuestion: TextView
    private lateinit var textAnswer1: TextView
    private lateinit var textAnswer2: TextView
    private var round: Int = 1
    private lateinit var textName: TextView
    private var name: String? = null
    private var status: String? = null
    private var playerCount: Int = 0
    private lateinit var playerAvatar: String
    private lateinit var playerName: String
    private lateinit var adminAvatar: String
    private lateinit var adminName: String
    private lateinit var database: DatabaseReference
    private var player: String? = null
    private lateinit var bear: ImageView
    private lateinit var rabbit: ImageView
    private lateinit var owl: ImageView
    private lateinit var horse: ImageView
    private lateinit var bird: ImageView
    private lateinit var sheep: ImageView
    private lateinit var gameRef: DatabaseReference
    val LIST: MutableList<MutableMap<String, String>> = mutableListOf()
    private var Questions: MutableList<String> = mutableListOf()
    private var Answers4: MutableList<String> = mutableListOf()
    private val playerList = mutableMapOf<String, String>() // Имена и аватарки игроков
    private val myList = mutableListOf<String>() // Айди игроков
    private val imageIDlist = listOf(
        R.drawable.bear,
        R.drawable.horse,
        R.drawable.rabbit,
        R.drawable.owl,
        R.drawable.bird,
        R.drawable.sheep
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_s)
        val gameID = intent.getStringExtra("IDD").toString() // код игры
        myList.add(gameID)
        player = intent.getStringExtra("player").toString() // код игрока

        textName = findViewById(R.id.textName)
        Buttona1 = findViewById(R.id.buttona1)
        Buttona2 = findViewById(R.id.buttona2)
        textQuestion = findViewById<TextView>(R.id.questionText)
        textAnswer1 = findViewById<TextView>(R.id.answer1)
        textAnswer2 = findViewById<TextView>(R.id.answer2)

        bear = findViewById(R.id.bear_av)
        sheep = findViewById(R.id.sheep_av)
        bird = findViewById(R.id.bird_av)
        horse = findViewById(R.id.horse_av)
        owl = findViewById(R.id.owl_av)
        rabbit = findViewById(R.id.rabbit_av)

        database = FirebaseDatabase.getInstance().reference
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                status = dataSnapshot.child("users").child(player!!).child("status")
                    .getValue(String::class.java).toString()
                if (status == "player"){
                    name = dataSnapshot.child("users").child(player!!).child("name")
                        .getValue(String::class.java).toString()} // имя этого игрока
                else {
                    name = dataSnapshot.child("users").child(gameID).child("name")
                        .getValue(String::class.java).toString() // имя админа
                    player = gameID
                }
                textName.text = name
                playerCount = dataSnapshot.child("game").child(gameID).child("count").getValue(Int::class.java)!!
                adminName = dataSnapshot.child("users").child(gameID).child("name").getValue(String::class.java).toString()
                adminAvatar = dataSnapshot.child("users").child(gameID).child("avatar").getValue(String::class.java).toString()
                playerList[adminName] = adminAvatar

                for (playerSnapshot in dataSnapshot.child("game").child(gameID).child("players").children) {
                    val playerID = playerSnapshot.child("name").getValue(String::class.java)
                    playerAvatar = dataSnapshot.child("users").child(playerID.toString()).child("avatar").getValue(String::class.java).toString()
                    playerName = dataSnapshot.child("users").child(playerID.toString()).child("name").getValue(String::class.java).toString()
                    playerList.put(playerName, playerAvatar)
                    myList.add(playerID.toString())
                    Log.d("ktoKOKO", playerID.toString())
                    Log.d("pocenuKOKO", myList.toString())


                }
                for (playerSnapshot in dataSnapshot.child("game").child(gameID).child("questions").children) {
                    val q = playerSnapshot.child("val").getValue(String::class.java)
                    Questions.add(q.toString())
                    Log.d("KIKI", q.toString())
                }
                val usersRef = FirebaseDatabase.getInstance().getReference("users")

                for (pl in myList) {
                    val userNode = usersRef.child(pl)

                    userNode.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val qa11 = dataSnapshot.child("a11").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa11 != null) {
                                Log.d("CHTOOO", qa11.toString())
                                LIST.add(qa11 as MutableMap<String, String>)
                            }
                            var qa12 = dataSnapshot.child("a12").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa12 != null) {
                                Log.d("CHTOOO", qa12.toString())
                                LIST.add(qa12 as MutableMap<String, String>)
                            }
                            var qa13 = dataSnapshot.child("a13").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa13 != null) {
                                Log.d("CHTOOO", qa13.toString())
                                LIST.add(qa13 as MutableMap<String, String>)
                            }
                            var qa21 = dataSnapshot.child("a21").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa21 != null) {
                                Log.d("CHTOOO", qa21.toString())
                                LIST.add(qa21 as MutableMap<String, String>)
                            }
                            var qa22 = dataSnapshot.child("a22").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa22 != null) {
                                Log.d("CHTOOO", qa22.toString())
                                LIST.add(qa22 as MutableMap<String, String>)
                            }
                            var qa23 = dataSnapshot.child("a23").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa23 != null) {
                                Log.d("CHTOOO", qa23.toString())
                                LIST.add(qa23 as MutableMap<String, String>)
                            }
                            var qa31 = dataSnapshot.child("a31").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa31 != null) {
                                Log.d("CHTOOO", qa31.toString())
                                LIST.add(qa31 as MutableMap<String, String>)
                            }
                            var qa32 = dataSnapshot.child("a32").getValue(object : GenericTypeIndicator<Map<String, String>>() {})
                            if (qa32 != null) {
                                Log.d("CHTOOO", qa32.toString())
                                LIST.add(qa32 as MutableMap<String, String>)
                            }

                            Log.d("LISTTLOL", LIST.toString())
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Обработка ошибок
                        }
                    })
                }



                if (playerList.get(name).toString().takeLast(11) == "ar2_button}") {
                    bear.visibility = View.VISIBLE
                } else if (playerList.get(name).toString().takeLast(11) == "ep2_button}") {
                    sheep.visibility = View.VISIBLE
                } else if (playerList.get(name).toString().takeLast(11) == "se2_button}") {
                    horse.visibility = View.VISIBLE
                } else if (playerList.get(name).toString().takeLast(11) == "it2_button}") {
                    rabbit.visibility = View.VISIBLE
                } else if (playerList.get(name).toString().takeLast(11) == "wl2_button}") {
                    owl.visibility = View.VISIBLE
                } else if (playerList.get(name).toString().takeLast(11) == "rd2_button}") {
                    bird.visibility = View.VISIBLE
                }
                updateQuestion()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun updateQuestion() {
        // Проверяем, не закончились ли вопросы
        if (currentQuestionIndex < Questions.size) {
            // Получаем текущий вопрос
            val currentQuestion = Questions[currentQuestionIndex]

            // Перемешиваем LIST (это нужно делать для каждого вопроса)
            LIST.shuffle(Random(System.currentTimeMillis()))

            // Устанавливаем текст вопроса
            textQuestion.text = currentQuestion

            // Получаем ответы от первых двух игроков
            textAnswer1.text = LIST[0].values.first().toString()
            textAnswer2.text = LIST[1].values.first().toString()

            // Устанавливаем обработчики кликов для кнопок
            Buttona1.setOnClickListener {
                // Переходим к следующему вопросу
                currentQuestionIndex++
                updateQuestion()
            }

            Buttona2.setOnClickListener {
                // Переходим к следующему вопросу
                currentQuestionIndex++
                updateQuestion()
            }

        } else {
            // Обработка окончания игры (например, показать сообщение "Игра окончена!")
        }
    }
}
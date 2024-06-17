package com.bignerdranch.android.findme
// Голосование в режиме "Незнакомцы", в котором игроки выбирают лучший ответ из предложенных двух.
// Баллы делятся в соотношении выбранных и начисляются авторам ответов.

import android.annotation.SuppressLint
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
import com.google.firebase.database.ValueEventListener

class VotingS : AppCompatActivity() {
    private var currentQuestionIndex = 1
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
    @SuppressLint("MissingInflatedId")
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

                    // Получение ответов игрока
                    val a11 = playerSnapshot.child("a11").getValue(String::class.java).toString()
                    val a12 = playerSnapshot.child("a12").getValue(String::class.java).toString()
                    val a13 = playerSnapshot.child("a13").getValue(String::class.java).toString()
                    val a14 = playerSnapshot.child("a14").getValue(String::class.java).toString()
                    val a21 = playerSnapshot.child("a21").getValue(String::class.java).toString()
                    val a22 = playerSnapshot.child("a22").getValue(String::class.java).toString()
                    val a23 = playerSnapshot.child("a23").getValue(String::class.java).toString()
                    val a24 = playerSnapshot.child("a24").getValue(String::class.java).toString()
                    val a25 = playerSnapshot.child("a25").getValue(String::class.java).toString()
                    val a31 = playerSnapshot.child("a31").getValue(String::class.java).toString()
                    val a32 = playerSnapshot.child("a32").getValue(String::class.java).toString()
                    val a33 = playerSnapshot.child("a33").getValue(String::class.java).toString()
                    val a34 = playerSnapshot.child("a34").getValue(String::class.java).toString()
                    val a35 = playerSnapshot.child("a35").getValue(String::class.java).toString()
                    Answers4.add(a11)
                    Answers4.add(a12)
                    Answers4.add(a13)
                    Answers4.add(a21)
                    Answers4.add(a22)
                    Answers4.add(a23)
                    Answers4.add(a31)
                    Log.d("answersERROR", Answers4.toString())


                }
                for (playerSnapshot in dataSnapshot.child("game").child(gameID).child("questions").children) {
                    val q = playerSnapshot.getValue(String::class.java)
                    Questions.add(q.toString())
                }

                for (i in 0 until Questions.size) {
                    val question = Questions[i]
                    val questionMap = mutableMapOf<String, String>()
                    for (j in myList.indices) {
                        val player = myList[j]
                        val answer = Answers4[i]
                        questionMap[question] = answer
                    }

                    LIST.add(questionMap)}

                Log.d("KONEC", LIST.toString())
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
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )}}

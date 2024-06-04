package com.bignerdranch.android.findme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.findme.databinding.ActivityWaitBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

// Ожидание для админа
class Wait : AppCompatActivity() {
    private lateinit var startButton23: ImageButton
    private lateinit var binding: ActivityWaitBinding
    private val adapter = PlayerAdapter()
    private lateinit var database: DatabaseReference
    private val playerList = mutableMapOf<String, String>()
    private val imageIDlist = listOf(
        R.drawable.bear,
        R.drawable.horse,
        R.drawable.rabbit,
        R.drawable.owl,
        R.drawable.bird,
        R.drawable.sheep
    )
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startButton23 = findViewById(R.id.start23)
        init()
    }
    private fun init() {
        val recyclerView: RecyclerView = binding.recyclerview
        recyclerView.layoutManager = GridLayoutManager(this@Wait, 1)
        recyclerView.adapter = adapter
        var playerAvatar: String = ""
        var playerName: String = ""
        var playerCount : Int = 1
        var playerCount2 : Int = 1
        var k: Int = 2
        val ID = intent.getStringExtra("playerName").toString() // код админа = код игры
        database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                adapter.clearPlayers(adapter)
                playerAvatar = dataSnapshot.child("users").child(ID).child("avatar").getValue(String::class.java).toString()
                playerName = dataSnapshot.child("users").child(ID).child("name").getValue(String::class.java).toString()

                if (playerAvatar != "") {
                    var playerAvatar1 = 0
                    when {
                        playerAvatar.takeLast(11) == "ar2_button}" -> playerAvatar1 = imageIDlist[0]
                        playerAvatar.takeLast(11) == "se2_button}" -> playerAvatar1 = imageIDlist[1]
                        playerAvatar.takeLast(11) == "it2_button}" -> playerAvatar1 = imageIDlist[2]
                        playerAvatar.takeLast(11) == "wl2_button}" -> playerAvatar1 = imageIDlist[3]
                        playerAvatar.takeLast(11) == "rd2_button}" -> playerAvatar1 = imageIDlist[4]
                        playerAvatar.takeLast(11) == "ep2_button}" -> playerAvatar1 = imageIDlist[5]
                        else -> Toast.makeText(this@Wait, ID, Toast.LENGTH_SHORT).show()
                    }
                    val player = Player(playerAvatar1, playerName)
                    adapter.addPlayer(player)
                }

                playerCount = dataSnapshot.child("game").child(ID).child("count").getValue(Int::class.java)!!
                playerCount2 = playerCount

                for (playerSnapshot in dataSnapshot.child("game").child(ID).child("players").children) {
                    val playerID = playerSnapshot.child("name").getValue(String::class.java)
                    playerAvatar = dataSnapshot.child("users").child(playerID.toString()).child("avatar").getValue(String::class.java).toString()
                    playerName = dataSnapshot.child("users").child(playerID.toString()).child("name").getValue(String::class.java).toString()
                    playerList.put(playerName, playerAvatar)
                    k++
                }

                for ((Name, Avatar) in playerList) {
                    if (Avatar != "") {
                        var playerAvatar1 = 0
                        when {
                            Avatar.takeLast(11) == "ar2_button}" -> playerAvatar1 = imageIDlist[0]
                            Avatar.takeLast(11) == "se2_button}" -> playerAvatar1 = imageIDlist[1]
                            Avatar.takeLast(11) == "it2_button}" -> playerAvatar1 = imageIDlist[2]
                            Avatar.takeLast(11) == "wl2_button}" -> playerAvatar1 = imageIDlist[3]
                            Avatar.takeLast(11) == "rd2_button}" -> playerAvatar1 = imageIDlist[4]
                            Avatar.takeLast(11) == "ep2_button}" -> playerAvatar1 = imageIDlist[5]
                        }
                        val player = Player(playerAvatar1, Name)
                        adapter.addPlayer(player)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val textView = findViewById<TextView>(R.id.textView)
        startButton23.setOnClickListener {
            startButton23.isEnabled = false

            if (playerCount2 >= 4) {
                startButton23.isEnabled = true
                startButton23.setColorFilter(null)
                textView.text = "Начать игру"
                val intent = Intent(this@Wait, AnswersAdmin::class.java)
                intent.putExtra("gamecode", ID)
                intent.putExtra("count", playerCount2.toString())

                // Выбираем случайные _ вопросов
                val randomQuestions = when (playerCount2) {
                    4 -> questions.shuffled().take(8)
                    5 -> questions.shuffled().take(10)
                    6 -> questions.shuffled().take(9)
                    7 -> questions.shuffled().take(14)
                    8 -> questions.shuffled().take(12)
                    else -> listOf() // По умолчанию пустой список, если playerCount2 не соответствует ни одному из условий
                }

                val database = Firebase.database.reference.child("game").child(ID).child("questions")
                for (i in 0 until randomQuestions.size) {
                    database.child("w${i + 1}").setValue(randomQuestions[i])
                }

                startActivity(intent)
            }
        }
    }
}
package com.bignerdranch.android.findme
// Голосование в режиме "Незнакомцы", в котором игроки выбирают лучший ответ из предложенных двух.
// Баллы делятся в соотношении выбранных и начисляются авторам ответов.
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VotingS : AppCompatActivity() {
    private var round: Int = 1
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
    private val playerList = mutableMapOf<String, String>() // имена и аватарки всех игроков и админа
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
        player = intent.getStringExtra("player").toString() // код игрока



        bear = findViewById(R.id.bear_av)
        sheep = findViewById(R.id.sheep_av)
        bird = findViewById(R.id.bird_av)
        horse = findViewById(R.id.horse_av)
        owl = findViewById(R.id.owl_av)
        rabbit = findViewById(R.id.rabbit_av)

        database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                status = dataSnapshot.child("users").child(player!!).child("status")
                    .getValue(String::class.java).toString()
                if (status == "player"){
                name = dataSnapshot.child("users").child(player!!).child("name")
                    .getValue(String::class.java).toString()} // имя этого игрока
                else {
                    name = dataSnapshot.child("users").child(gameID).child("name")
                        .getValue(String::class.java).toString() // имя админа
                }
                playerCount = dataSnapshot.child("game").child(gameID).child("count")
                    .getValue(Int::class.java)!!
                adminName = dataSnapshot.child("users").child(gameID).child("name").getValue(String::class.java).toString()
                adminAvatar = dataSnapshot.child("users").child(gameID).child("avatar").getValue(String::class.java).toString()
                playerList[adminName] = adminAvatar
                for (playerSnapshot in dataSnapshot.child("game").child(gameID).child("players").children) {
                    val playerID = playerSnapshot.child("name").getValue(String::class.java)
                    playerAvatar =
                        dataSnapshot.child("users").child(playerID.toString()).child("avatar")
                            .getValue(String::class.java).toString()
                    playerName =
                        dataSnapshot.child("users").child(playerID.toString()).child("name")
                            .getValue(String::class.java).toString()
                    playerList.put(playerName, playerAvatar)
                }
                if (playerList.get(name).toString().takeLast(11) == "ar2_button}") {
                    bear.visibility = View.VISIBLE
                }
                else if (playerList.get(name).toString().takeLast(11) == "ep2_button}") {
                    sheep.visibility = View.VISIBLE
                }
                else if (playerList.get(name).toString().takeLast(11) == "se2_button}") {
                    horse.visibility = View.VISIBLE
                }
                else if (playerList.get(name).toString().takeLast(11) == "it2_button}") {
                    rabbit.visibility = View.VISIBLE
                }
                else if (playerList.get(name).toString().takeLast(11) == "wl2_button}") {
                    owl.visibility = View.VISIBLE
                }
                else if (playerList.get(name).toString().takeLast(11) == "rd2_button}") {
                    bird.visibility = View.VISIBLE
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
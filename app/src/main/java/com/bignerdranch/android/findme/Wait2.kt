package com.bignerdranch.android.findme
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.findme.databinding.ActivityWait2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class Wait2 : AppCompatActivity() {
    private lateinit var binding: ActivityWait2Binding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWait2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val recyclerView: RecyclerView = binding.recyclerview
        recyclerView.layoutManager = GridLayoutManager(this@Wait2, 1)
        recyclerView.adapter = adapter
        var playerAvatar: String = ""
        var adminAvatar: String = ""
        var adminName: String = ""
        var playerName: String = ""
        var k: Int = 2
        val GameCode = intent.getStringExtra("GameCode").toString() // код игры = код админа
        database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                adapter.clearPlayers(adapter)
                adminName = dataSnapshot.child("users").child(GameCode).child("name").getValue(String::class.java).toString()
                adminAvatar = dataSnapshot.child("users").child(GameCode).child("avatar").getValue(String::class.java).toString()
                playerList[adminName] = adminAvatar
                for (playerSnapshot in dataSnapshot.child("game").child(GameCode).child("players").children) {
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
                // Handle onCancelled event
            }
        })
    }
}
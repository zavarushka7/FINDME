package com.bignerdranch.android.findme

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.findme.databinding.ActivityWaitBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Wait : AppCompatActivity() {
    private lateinit var binding: ActivityWaitBinding
    private val adapter = PlayerAdapter()
    private lateinit var database: DatabaseReference
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
        binding = ActivityWaitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val recyclerView: RecyclerView = binding.recyclerview
        recyclerView.layoutManager = GridLayoutManager(this@Wait, 1)
        recyclerView.adapter = adapter

        var playerAvatar: String = ""
        var playerName: String = ""
        val ID = intent.getStringExtra("code").toString()
        database = FirebaseDatabase.getInstance().reference.child("users").child(ID)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                playerAvatar = dataSnapshot.child("avatar").getValue(String::class.java).toString()
                playerName = dataSnapshot.child("name").getValue(String::class.java).toString()

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
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}

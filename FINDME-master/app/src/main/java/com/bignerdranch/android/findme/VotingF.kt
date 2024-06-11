package com.bignerdranch.android.findme
//Голосование в режиме "Друзья", в котором игроки угадывают кому могут принадлежать эти 2 ответа.
// Баллы начисляются тебе, если ты угадал правильно кому принадлежит ответ.

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.DragEvent.*
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.findme.databinding.ActivityVotingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VotingF : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {
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
    private lateinit var mViewBinding: ActivityVotingBinding
    private val msg = "DragDrop"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)
        mViewBinding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        mViewBinding.but1.setOnTouchListener(this)
        mViewBinding.but2.setOnDragListener(this)

        



        init()
    }






    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return if (event!!.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(v)
            v?.startDragAndDrop(data, shadowBuilder, v, 0)
            true
        } else {
            false
        }
    }
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event?.action) {
            ACTION_DRAG_STARTED -> {
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
            }
            ACTION_DRAG_ENTERED -> {
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED")
            }
            ACTION_DRAG_EXITED -> {
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED")
            }
            ACTION_DRAG_LOCATION -> {
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION")
            }
            ACTION_DRAG_ENDED -> {
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED")
            }
            ACTION_DROP -> {
                Log.d(msg, "ACTION_DROP event")
                mViewBinding.but2.setImageResource(R.drawable.bigredbutton)
                mViewBinding.but1.visibility = INVISIBLE
                Toast.makeText(this@VotingF,"You have matched the shape!!",Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }


    private fun init() {
        val recyclerView: RecyclerView = mViewBinding.avatars
        recyclerView.layoutManager = GridLayoutManager(this@VotingF, 6, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        var playerAvatar: String = ""
        var playerName: String = ""
        var playerCount : Int = 1
        var playerCount2 : Int = 1
        var k: Int = 2
        val ID = intent.getStringExtra("IDD").toString() // код админа = код игры
        database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                adapter.clearPlayers(adapter)
                playerAvatar = dataSnapshot.child("users").child(ID).child("avatar")
                    .getValue(String::class.java).toString()
                playerName =
                    dataSnapshot.child("users").child(ID).child("name").getValue(String::class.java)
                        .toString()

                if (playerAvatar != "") {
                    var playerAvatar1 = 0
                    when {
                        playerAvatar.takeLast(11) == "ar2_button}" -> playerAvatar1 = imageIDlist[0]
                        playerAvatar.takeLast(11) == "se2_button}" -> playerAvatar1 = imageIDlist[1]
                        playerAvatar.takeLast(11) == "it2_button}" -> playerAvatar1 = imageIDlist[2]
                        playerAvatar.takeLast(11) == "wl2_button}" -> playerAvatar1 = imageIDlist[3]
                        playerAvatar.takeLast(11) == "rd2_button}" -> playerAvatar1 = imageIDlist[4]
                        playerAvatar.takeLast(11) == "ep2_button}" -> playerAvatar1 = imageIDlist[5]
                        else -> Toast.makeText(this@VotingF, ID, Toast.LENGTH_SHORT).show()
                    }
                    val player = Player(playerAvatar1, playerName)
                    adapter.addPlayer(player)
                }

                playerCount =
                    dataSnapshot.child("game").child(ID).child("count").getValue(Int::class.java)!!
                playerCount2 = playerCount

                for (playerSnapshot in dataSnapshot.child("game").child(ID)
                    .child("players").children) {
                    val playerID = playerSnapshot.child("name").getValue(String::class.java)
                    playerAvatar =
                        dataSnapshot.child("users").child(playerID.toString()).child("avatar")
                            .getValue(String::class.java).toString()
                    playerName =
                        dataSnapshot.child("users").child(playerID.toString()).child("name")
                            .getValue(String::class.java).toString()
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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
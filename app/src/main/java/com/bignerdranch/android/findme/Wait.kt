package com.bignerdranch.android.findme

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.leanback.media.PlayerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class GameActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var gameRef: DatabaseReference
    private lateinit var usersRef: DatabaseReference
    private lateinit var gameListener: ValueEventListener
    private var gameKey: String = ""
    private var playersMap: HashMap<String, Player> = HashMap()
    private lateinit var playerAdapter: PlayerAdapter // Создайте адаптер для отображения игроков
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)
        database = Firebase.database.reference
        gameKey = intent.getStringExtra("game_key") ?: ""
        gameRef = database.child("game").child(gameKey)
        usersRef = database.child("users")
        // Инициализация RecyclerView для отображения игроков
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        playerAdapter = PlayerAdapter(playersMap.values.toList())
        recyclerView.adapter = playerAdapter
        gameListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val game = dataSnapshot.getValue(Game::class.java)
                game?.let {
                    supportActionBar?.title = "Game ${gameKey}"
                    playersMap.clear()
                    playersMap[game.admin] = Player(game.admin, "Admin") // Добавляем админа
                    game.players?.forEach { playerKey ->
                        usersRef.child(playerKey).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(userSnapshot: DataSnapshot) {
                                val user = userSnapshot.getValue(User::class.java)
                                user?.let {
                                    playersMap[playerKey] = Player(playerKey, user.name, user.avatar)
                                    updatePlayersList()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.w(TAG, "Failed to read user value.", error.toException())
                            }
                        })
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read game value.", error.toException())
            }
        }
        gameRef.addValueEventListener(gameListener)
    }
    private fun updatePlayersList() {
        playerAdapter.updatePlayersList(playersMap.values.toList())
    }
    override fun onDestroy() {
        super.onDestroy()
        gameRef.removeEventListener(gameListener)
    }
}
data class Game(
    val admin: String = "",
    val code: String = "",
    val count: Int = 0,
    val mode: String = "",
    val players: List<String>? = null
)
data class User(
    val avatar: String = "",
    val key: String = "",
    val name: String = "",
    val status: String = ""
)
data class Player(
    val key: String,
    val name: String,
    val avatar: String = ""
)
package com.bignerdranch.android.findme
// Ожидание ответов всех игроков
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Ready : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var mode: String
    private var r: Int = 0
    private var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)
        val gameID = intent.getStringExtra("IDD").toString()
        val player = intent.getStringExtra("player").toString()
        database = FirebaseDatabase.getInstance().reference.child("game").child(gameID)
        database2 = FirebaseDatabase.getInstance().reference.child("users").child(gameID)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mode1 = dataSnapshot.child("mode").getValue(String::class.java)
                mode = mode1.toString()
                val r1 = dataSnapshot.child("ready").getValue(Int::class.java)
                r = r1 ?: 0
                val count1 = dataSnapshot.child("count").getValue(Int::class.java)
                count = count1 ?: 0

                if (r == count) {
                    if(mode == "friends2"){
                        val intent = Intent(this@Ready, VotingF::class.java)
                        intent.putExtra("IDD", gameID)
                        intent.putExtra("player", player)
                        startActivity(intent)
                    }
                    else if (mode == "strangers2"){
                        val intent = Intent(this@Ready, VotingS::class.java)
                        intent.putExtra("IDD", gameID)
                        intent.putExtra("player", player)
                        startActivity(intent)
                    }
                }




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}
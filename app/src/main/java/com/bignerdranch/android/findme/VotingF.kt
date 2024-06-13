package com.bignerdranch.android.findme
//Голосование в режиме "Друзья", в котором игроки угадывают кому могут принадлежать эти 2 ответа.
// Баллы начисляются тебе, если ты угадал правильно кому принадлежит ответ.

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.DragEvent.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var draggedItem: String? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.but1.setOnDragListener(this)
        mViewBinding.but2.setOnDragListener(this)
        val recyclerView: RecyclerView = mViewBinding.avatars
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) {
                    val child = rv.findChildViewUnder(e.x, e.y)
                    if (child != null) {
                        draggedItem = adapter.getItem(rv.getChildAdapterPosition(child)).toString()
                        val item = ClipData.Item(draggedItem)
                        val dragData = ClipData(
                            "dragData",
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                            item
                        )
                        val shadowBuilder = View.DragShadowBuilder(child)
                        child.startDragAndDrop(dragData, shadowBuilder, child, 0)
                        return true
                    }
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
        init()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == mViewBinding.but1 || v == mViewBinding.but2) {
            return false // Отменяем перетаскивание для кнопок but1 и but2
        }

        val locationBut1 = IntArray(2)
        mViewBinding.but1.getLocationOnScreen(locationBut1)
        val but1X = locationBut1[0]
        val but1Y = locationBut1[1]

        val locationBut2 = IntArray(2)
        mViewBinding.but2.getLocationOnScreen(locationBut2)
        val but2X = locationBut2[0] + mViewBinding.but2.width
        val but2Y = locationBut2[1] + mViewBinding.but2.height

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                v?.startDragAndDrop(data, shadowBuilder, v, 0)
                v?.visibility = View.INVISIBLE // Скрываем элемент при начале перетаскивания
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val params = v?.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = event.rawX.toInt()
                if (event.rawX > but2X) {
                    params.leftMargin = but2X - v.width // Держим элемент справа на кнопке but2
                } else if (event.rawX < but1X) {
                    params.leftMargin = but1X // Держим элемент справа на кнопке but1
                }
                v.layoutParams = params
            }
            MotionEvent.ACTION_UP -> {
                v?.visibility = View.VISIBLE // Восстанавливаем видимость элемента при завершении перетаскивания
            }
        }
        return false
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event?.action) {
            DragEvent.ACTION_DROP -> {
                // Получаем данные из DragEvent
                val item: ClipData.Item = event.clipData.getItemAt(0)
                val dragData = item.text.toString()
                if (v is ImageView) {
                    val draggedView = event.localState as View // Получаем view, которое было перетаскиваемо
                    val owner = draggedView.parent as ViewGroup // Получаем родительскую view
                    owner.removeView(draggedView) // Удаляем view из родителя
                    val container = v.parent as ViewGroup // Получаем родительскую view для целевого ImageView
                    container.addView(draggedView) // Добавляем view в контейнер целевого ImageView
                    draggedView.x = event.x - draggedView.width / 2 // Устанавливаем позицию по X
                    draggedView.y = event.y - draggedView.height / 2 // Устанавливаем позицию по Y
                    draggedView.visibility = View.VISIBLE // Делаем view видимым
                }
            }
        }
        return true
    }



    private fun init() {
        val recyclerView: RecyclerView = mViewBinding.avatars
        recyclerView.layoutManager = GridLayoutManager(this@VotingF, 4, GridLayoutManager.VERTICAL, false)
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
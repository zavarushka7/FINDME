package com.bignerdranch.android.findme

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.findme.databinding.PlayerItemBinding

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    val playerList = ArrayList<Player>()

    class PlayerHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = PlayerItemBinding.bind(item)
        fun bind(player: Player) = with(binding){
            ava.setImageResource(player.avatar)
            nam.text = player.player
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerHolder(view)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(playerList[position])
    }

    fun addPlayer(player: Player){
        playerList.add(player)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearPlayers(adapter: PlayerAdapter) {
        adapter.playerList.clear()
        adapter.notifyDataSetChanged()
    }
    fun getItem(position: Int): Player {
        return playerList[position]
    }

}



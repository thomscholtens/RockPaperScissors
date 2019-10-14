package com.example.rockpaperscissors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.game_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context.applicationContext
        private val tvResult: TextView = itemView.findViewById(R.id.tvResult)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val ivComputer: ImageView = itemView.findViewById(R.id.ivComputer)
        private val ivYou: ImageView = itemView.findViewById(R.id.ivPlayer)

        fun bind(game: Game) {
            when (game.result) {
                Game.Result.LOSE -> tvResult.text = context.getString(R.string.lose)
                Game.Result.DRAW -> tvResult.text = context.getString(R.string.draw)
                Game.Result.WIN -> tvResult.text = context.getString(R.string.win)
            }
            tvDate.text = game.date.toString()
            ivComputer.setImageDrawable(itemView.context.applicationContext.getDrawable(game.computerChoice))
            ivYou.setImageDrawable(itemView.context.applicationContext.getDrawable(game.playerChoice))
        }
    }
}
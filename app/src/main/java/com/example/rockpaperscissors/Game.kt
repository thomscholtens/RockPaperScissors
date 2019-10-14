package com.example.rockpaperscissors

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game(

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "playerChoice")
    var playerChoice: Int,

    @ColumnInfo(name = "computerChoice")
    var computerChoice: Int,

    @ColumnInfo(name = "result")
    var result: Result,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable {

    enum class Result(val value: String) {
        WIN("win"),
        DRAW("draw"),
        LOSE("lose")
    }

    enum class Choice {
        ROCK,
        PAPER,
        SCISSORS
    }
}
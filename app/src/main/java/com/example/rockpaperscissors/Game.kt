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

    enum class Result(val value: Int) {
        WIN(1),
        DRAW(0),
        LOSE(-1)
    }

    enum class Choice {
        ROCK,
        PAPER,
        SCISSORS
    }
}
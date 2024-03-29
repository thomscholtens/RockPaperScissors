package com.example.rockpaperscissors

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun resultToInt(result: Game.Result): String {
        return result.value
    }

    @TypeConverter
    fun intToResult(value: String): Game.Result? {
        return Game.Result.values().first { it.value == value }
    }
}
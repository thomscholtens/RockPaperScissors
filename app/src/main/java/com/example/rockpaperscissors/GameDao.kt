package com.example.rockpaperscissors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(result) FROM game_table WHERE result = 1")
    suspend fun getNumberOfWins(): Int

    @Query("SELECT COUNT(result) FROM game_table WHERE result = 0")
    suspend fun getNumberOfDraws(): Int

    @Query("SELECT COUNT(result) FROM game_table WHERE result = -1")
    suspend fun getNumberOfLosses(): Int
}
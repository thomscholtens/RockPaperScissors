package com.example.rockpaperscissors

import android.content.Context

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getNumberOfWins(): Int {
        return gameDao.getNumberOfWins()
    }

    suspend fun getNumberOfDraws(): Int {
        return gameDao.getNumberOfDraws()
    }

    suspend fun getNumberOfLosses(): Int {
        return gameDao.getNumberOfLosses()
    }
}
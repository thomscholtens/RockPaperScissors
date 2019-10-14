package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.tvResult
import kotlinx.android.synthetic.main.game_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        btnRock.setOnClickListener { onPlayerChoice(Game.Choice.ROCK) }
        btnPaper.setOnClickListener { onPlayerChoice(Game.Choice.PAPER) }
        btnScissors.setOnClickListener { onPlayerChoice(Game.Choice.SCISSORS) }
        getStatistics()
    }

    private fun onPlayerChoice(playerChoice: Game.Choice) {
        val computerChoice = Game.Choice.values().random()
        // Set images
        ivComputerMove.setImageDrawable(getDrawable(getChoiceDrawableId(computerChoice)))
        ivPlayerMove.setImageDrawable(getDrawable(getChoiceDrawableId(playerChoice)))
        // Create game
        val game = Game(
            date = Date(),
            playerChoice = getChoiceDrawableId(playerChoice),
            computerChoice = getChoiceDrawableId(computerChoice),
            result = getGameResult(playerChoice, computerChoice)
        )
        // Set result text
        when {
            game.result == Game.Result.WIN -> tvResult.text = getString(R.string.win)
            game.result == Game.Result.DRAW -> tvResult.text = getString(R.string.draw)
            game.result == Game.Result.LOSE -> tvResult.text = getString(R.string.lose)
        }
        addGameToDatabase(game)
    }

    private fun getGameResult(playerChoice: Game.Choice, computerChoice: Game.Choice): Game.Result {
        if (playerChoice == computerChoice) {
            return Game.Result.DRAW
        }

        return if (
            playerChoice == Game.Choice.ROCK && computerChoice == Game.Choice.SCISSORS ||
            playerChoice == Game.Choice.PAPER && computerChoice == Game.Choice.ROCK ||
            playerChoice == Game.Choice.SCISSORS && computerChoice == Game.Choice.PAPER
        ) {
            Game.Result.WIN
        } else Game.Result.LOSE
    }

    private fun getChoiceDrawableId(choice: Game.Choice): Int {
        return when (choice) {
            Game.Choice.ROCK -> R.drawable.rock
            Game.Choice.PAPER -> R.drawable.paper
            Game.Choice.SCISSORS -> R.drawable.scissors
        }
    }

    private fun addGameToDatabase(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
                getStatistics()
            }
        }
    }

    private fun getStatistics() {
        CoroutineScope(Dispatchers.Main).launch {
            var wins = 0
            var draws = 0
            var losses = 0
            withContext(Dispatchers.IO) {
                wins = gameRepository.getNumberOfWins()
                draws = gameRepository.getNumberOfDraws()
                losses = gameRepository.getNumberOfLosses()
            }
            tvStatistics.text = getString(R.string.stats, wins, draws, losses)
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getStatistics()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_show_history -> {
                startHistoryActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

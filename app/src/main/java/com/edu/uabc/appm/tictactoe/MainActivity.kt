package com.edu.uabc.appm.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var gameEngine:Board = Board()
    lateinit var boardView:BoardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boardView = findViewById(R.id.board)
        gameEngine = Board()
        boardView.setGameEngine(gameEngine)
        boardView.setMainActivity(this)
        newGame()
    }

    private fun newGame() {
        gameEngine.newGame()
        boardView.invalidate()
    }

    fun gameEnded(c: Char) {
        val msg = if (c == 'T') "Termino Juego. Tie" else "Termino Juego. $c Ganador"

        AlertDialog.Builder(this).setTitle("Tic Tac Toe").setMessage(msg).setOnDismissListener { newGame() }.show()
    }
}

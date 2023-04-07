package com.example.my_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater

import com.example.my_game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.toNewGame?.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        binding.toContinueGame?.setOnClickListener {
            val data = getInfoAboutGame()
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(EXTRA_TIME, data.time)
                putExtra(EXTRA_GAME_FIELD, data.gameField)

            }
            startActivity(intent)
        }
        binding.toOptions?.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    private fun getInfoAboutGame(): Infogame {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)) {
            val time = getLong(GameActivity.PREF_TIME, 0L)
            val gameField = getString(GameActivity.PREF_GAME_FIELD, "")

            return if(gameField != null){
                Infogame(time, gameField)
            } else {
                Infogame(0L, "")
            }
        }
    }

    data class Infogame(val time: Long, val gameField: String)

    companion object{
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_GAME_FIELD = "extra_game_field"
    }
}
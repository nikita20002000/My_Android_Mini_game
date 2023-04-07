package com.example.my_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.example.my_game.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding

    private var currentSoundSound = 0
    private var currentLVL = 0
    private var currentRules = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionsBinding.inflate(layoutInflater)

        val data = getSettingsInfo()
        currentSoundSound = data.soundValue
        currentLVL = data.lvl
        currentRules = data.rules

        when(currentRules) {
            1 -> binding.checkBoxVertical.isChecked = true
            2 -> binding.checkBoxHorizontal.isChecked = true
            3 -> {
                binding.checkBoxVertical.isChecked = true
                binding.checkBoxHorizontal.isChecked = true
            }
            4 -> binding.checkBoxDiagonal.isChecked = true
            5 -> {
                binding.checkBoxDiagonal.isChecked = true
                binding.checkBoxVertical.isChecked = true
            }
            6 -> {
                binding.checkBoxDiagonal.isChecked = true
                binding.checkBoxHorizontal.isChecked = true
            }
            7 -> {
                binding.checkBoxDiagonal.isChecked = true
                binding.checkBoxHorizontal.isChecked = true
                binding.checkBoxVertical.isChecked = true
            }
        }

        if (currentLVL == 0){
            binding.previousLevel.visibility = View.INVISIBLE
        } else if (currentLVL == 2){
            binding.secondLevel.visibility = View.INVISIBLE
        }

        binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLVL]
        binding.soundBar.progress = currentSoundSound

        binding.toBack.setOnClickListener {
            onBackPressed()
        }



        binding.previousLevel.setOnClickListener {
            currentLVL--

            if (currentLVL == 0){
                binding.previousLevel.visibility = View.INVISIBLE
            } else if (currentLVL == 1){
                binding.secondLevel.visibility = View.VISIBLE
            }

            binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLVL]

            updateLvl(currentLVL)
        }

        binding.secondLevel.setOnClickListener {
            currentLVL++

            if (currentLVL == 1){
                binding.previousLevel.visibility = View.VISIBLE
            } else if (currentLVL == 2) {
                binding.secondLevel.visibility = View.INVISIBLE
            }

            binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLVL]

            updateLvl(currentLVL)
        }

        binding.soundBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                currentSoundSound = value
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateSoundValue(currentSoundSound)
            }
        })

        binding.checkBoxVertical.setOnCheckedChangeListener{_, isCheked ->
            if (isCheked){
                currentRules++
            }else{
                currentRules--
            }



            updateRules(currentRules)
        }

        binding.checkBoxHorizontal.setOnCheckedChangeListener{_, isCheked ->
            if (isCheked){
                currentRules+=2
            }else{
                currentRules-=2
            }

            updateRules(currentRules)
        }

        binding.checkBoxDiagonal.setOnCheckedChangeListener{_, isCheked ->
            if (isCheked){
                currentRules+=4
            }else{
                currentRules-=4
            }

            updateRules(currentRules)
        }

        setContentView(binding.root)
    }

    private fun updateSoundValue(value : Int) {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()){
            putInt(PREF_SOUND_VALUE, value)
            apply()
        }
        setResult(RESULT_OK)
    }
    private fun updateLvl(lvl : Int) {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()){
            putInt(PREF_LVL, lvl)
            apply()
        }
        setResult(RESULT_OK)
    }
    private fun updateRules(rules: Int){
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()){
            putInt(PREF_RULES, rules)
            apply()
        }
        setResult(RESULT_OK)
    }

    private fun getSettingsInfo() : SettingsInfo {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)) {
            val soundValue = getInt(PREF_SOUND_VALUE, 50)
            val lvl = getInt(PREF_LVL, 0)
            val rules = getInt(PREF_RULES, 7)

            return SettingsInfo(soundValue, lvl, rules)
        }
    }
    data class SettingsInfo(val soundValue: Int, val lvl: Int, val rules: Int)

    companion object{
        const val PREF_SOUND_VALUE = "pref_sound_value"
        const val PREF_LVL = "pref_lvl"
        const val PREF_RULES = "pref_rules"
    }
}
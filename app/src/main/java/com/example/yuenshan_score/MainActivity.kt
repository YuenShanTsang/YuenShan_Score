package com.example.yuenshan_score

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.yuenshan_score.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Init view binding
    private lateinit var binding: ActivityMainBinding

    // Declare share preferences
    private lateinit var sharedPrefs: SharedPreferences

    // Declare preferences key
    private val preferences = "MyScore"

    // Update score variable declaration
    private var teamAScore = 0
    private var teamBScore = 0
    private var changeScore = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the shared preferences object with the mode set to private
        sharedPrefs = getPreferences(Context.MODE_PRIVATE)

        // Set the listener for the switch
        binding.nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Apply the appropriate theme based on the user's preference
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Change the value of changeScore based on the selected radio button
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            /**allows the user to select the point value for each score
            by choosing one of the radio buttons in the radio group.**/
            when (checkedId) {
                R.id.radio1 -> changeScore = 1
                R.id.radio2 -> changeScore = 2
                R.id.radio3 -> changeScore = 3
            }

        }

        // Lambda function to set up the add and deduct buttons for Team A
        binding.addButtonA.setOnClickListener{
            // Increment the score for Team A by the changeScore value when add button is clicked
            teamAScore += changeScore
            // Save the current score of Team A in shared preferences
            if(switchChecked()){
                saveScore()
            }
            // Update the scoreboard for Team A
            updateTeamAScore()
        }

        binding.deductButtonA.setOnClickListener{
            // Decrement the score for Team A by the changeScore value when deduct button is clicked
            teamAScore -= changeScore
            // Save the current score of Team A in shared preferences
            if(switchChecked()){
                saveScore()
            }
            // Update the scoreboard for Team A
            updateTeamAScore()
        }

        // Lambda function to set up the add and deduct buttons for Team B
        binding.addButtonB.setOnClickListener{
            // Increment the score for Team B by the changeScore value when add button is clicked
            teamBScore += changeScore
            // Save the current score of Team B in shared preferences
            if(switchChecked()){
                saveScore()
            }
            // Update the scoreboard for Team B
            updateTeamBScore()
        }

        binding.deductButtonB.setOnClickListener{
            // Decrement the score for Team B by the changeScore value when deduct button is clicked
            teamBScore -= changeScore
            // Save the current score of Team B in shared preferences
            if(switchChecked()){
                saveScore()
            }
            // Update the scoreboard for Team B
            updateTeamBScore()
        }

    }
    private fun saveScore() {
        val sharedPreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("teamA_score", teamAScore.toString())
            putString("teamB_score", teamBScore.toString())
        }.apply()
    }

    // Function to update the scoreboard for Team A in the app UI
    private fun updateTeamAScore(){
        /**To ensure that the score is within the range of 0 to 200 by using coerceIn() function.
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamAScore = teamAScore.coerceIn(0, 200)
        // Display the updated score for Team A
        binding.scoreATextView.text = teamAScore.toString()
    }

    //Function to update the scoreboard for Team B in the app UI
    private fun updateTeamBScore(){
        /**To ensure that the score is within the range of 0 to 200 by using coerceIn() function.
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamBScore = teamBScore.coerceIn(0, 200)
        // Display the updated score for Team B
        binding.scoreBTextView.text = teamBScore.toString()
    }

    private fun switchChecked() : Boolean {

        return getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getBoolean("isChecked", false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about -> {
                val toast = Toast.makeText(this, "Developed by Yuen Shan Tsang (Clary)\nCourse Code: JAV1001", Toast.LENGTH_SHORT)
                toast.show()
                true
            }
            R.id.settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("teamAScore", binding.teamATextView.text)
                intent.putExtra("teamBScore", binding.teamBTextView.text)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


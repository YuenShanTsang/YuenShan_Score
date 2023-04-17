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

    // Initialize shared preferences variable
    private lateinit var sharedPrefs: SharedPreferences

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

        // Retrieve the saved scores from shared preferences
        sharedPrefs = getSharedPreferences("ScorePrefs", Context.MODE_PRIVATE)
        teamAScore = sharedPrefs.getString("teamA_score", "0")?.toInt() ?: 0
        teamBScore = sharedPrefs.getString("teamB_score", "0")?.toInt() ?: 0

        // Set the text views to display the saved scores
        binding.scoreATextView.text = teamAScore.toString()
        binding.scoreBTextView.text = teamBScore.toString()

        // Update the score if the switch is turned off
        val isEnabled = intent.getBooleanExtra("isEnabled", switchEnabled())
        if (!isEnabled) {
            teamAScore = 0
            teamBScore = 0
            binding.scoreATextView.text = "0"
            binding.scoreBTextView.text = "0"
        }

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
            // Update the scoreboard for Team A
            updateTeamAScore()
            // Save the score to SharedPreferences
            saveScore()
        }

        binding.deductButtonA.setOnClickListener{
            // Decrement the score for Team A by the changeScore value when deduct button is clicked
            teamAScore -= changeScore
            // Update the scoreboard for Team A
            updateTeamAScore()
            // Save the score to SharedPreferences
            saveScore()
        }

        // Lambda function to set up the add and deduct buttons for Team B
        binding.addButtonB.setOnClickListener{
            // Increment the score for Team B by the changeScore value when add button is clicked
            teamBScore += changeScore
            // Update the scoreboard for Team B
            updateTeamBScore()
            // Save the score to SharedPreferences
            saveScore()
        }

        binding.deductButtonB.setOnClickListener{
            // Decrement the score for Team B by the changeScore value when deduct button is clicked
            teamBScore -= changeScore
            // Update the scoreboard for Team B
            updateTeamBScore()
            // Save the score to SharedPreferences
            saveScore()
        }

        // Clear scores and update UI
        binding.clearButton.setOnClickListener {
            teamAScore = 0
            teamBScore = 0
            binding.scoreATextView.text = "0"
            binding.scoreBTextView.text = "0"
            saveScore()
        }
    }


    private fun switchEnabled() : Boolean {
        // Retrieve the value of the "EnabledSwitch" boolean preference from the "SavePrefs" SharedPreferences
        return getSharedPreferences("SavePrefs", Context.MODE_PRIVATE)
            .getBoolean("EnabledSwitch", false)
    }

    private fun saveScore() {
        if (switchEnabled()) {
            val editor = getSharedPreferences("ScorePrefs", Context.MODE_PRIVATE).edit()
            // Add the scores for team A and team B as String values to the editor
            editor.apply {
                putString("teamA_score", teamAScore.toString())
                putString("teamB_score", teamBScore.toString())
            }.apply()
        }
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

    // Create menu to the ActionBar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.home -> {
                // Go to main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about -> {
                // Toast that displays developer information
                val toast = Toast.makeText(this, "Developed by Yuen Shan Tsang (Clary)\nCourse Code: JAV1001", Toast.LENGTH_SHORT)
                toast.show()
                true
            }
            R.id.settings -> {
                // Go to setting activity
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


package com.example.yuenshan_score

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    // Team A scoreboard variable declaration
    private lateinit var scoreATextView: TextView
    private lateinit var addButtonA: Button
    private lateinit var deductButtonA: Button

    // Team B scoreboard variable declaration
    private lateinit var scoreBTextView: TextView
    private lateinit var addButtonB: Button
    private lateinit var deductButtonB: Button

    // Change points display variable declaration
    private lateinit var selectPoint: RadioGroup

    // Update score variable declaration
    private var teamAScore = 0
    private var teamBScore = 0
    private var changeScore = 1

    private lateinit var nightModeSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views and preferences
        nightModeSwitch = findViewById(R.id.night_mode_switch)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Set the initial state of the switch based on the user's preference
        nightModeSwitch.isChecked = sharedPreferences.getBoolean("night_mode", false)

        // Set the listener for the switch
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save the user's preference for night mode
            sharedPreferences.edit().putBoolean("night_mode", isChecked).apply()

            // Apply the appropriate theme based on the user's preference
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Set the background drawable based on the night mode state
            if (isChecked) {
                window.setBackgroundDrawableResource(R.drawable.night_background)
            } else {
                window.setBackgroundDrawableResource(R.drawable.day_background)
            }
        }

        // Set the background drawable based on the current night mode state
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            window.setBackgroundDrawableResource(R.drawable.night_background)
            nightModeSwitch.isChecked = true
        } else {
            window.setBackgroundDrawableResource(R.drawable.day_background)
            nightModeSwitch.isChecked = false
        }

        // Initialize Team A views
        scoreATextView = findViewById(R.id.scoreA_text_view)
        addButtonA = findViewById(R.id.add_buttonA)
        deductButtonA = findViewById(R.id.deduct_buttonA)

        // Initialize Team B views
        scoreBTextView = findViewById(R.id.scoreB_text_view)
        addButtonB = findViewById(R.id.add_buttonB)
        deductButtonB = findViewById(R.id.deduct_buttonB)

        // Initialize change points views
        selectPoint = findViewById(R.id.radioGroup)

        // Change the value of changeScore based on the selected radio button
        selectPoint.setOnCheckedChangeListener { _, checkedId ->
            /**allows the user to select the point value for each score
            by choosing one of the radio buttons in the radio group.**/
            when (checkedId) {
                R.id.radio1 -> changeScore = 1
                R.id.radio2 -> changeScore = 2
                R.id.radio3 -> changeScore = 3
            }
        }

        // Lambda function to set up the add and deduct buttons for Team A
        addButtonA.setOnClickListener{
            // Increment the score for Team A by the changeScore value when add button is clicked
            teamAScore += changeScore
            // Update the scoreboard for Team A
            updateTeamAScore()
        }
        deductButtonA.setOnClickListener{
            // Decrement the score for Team A by the changeScore value when deduct button is clicked
            teamAScore -= changeScore
            // Update the scoreboard for Team A
            updateTeamAScore()
        }

        // Lambda function to set up the add and deduct buttons for Team B
        addButtonB.setOnClickListener{
            // Increment the score for Team B by the changeScore value when add button is clicked
            teamBScore += changeScore
            // Update the scoreboard for Team B
            updateTeamBScore()
        }
        deductButtonB.setOnClickListener{
            // Decrement the score for Team B by the changeScore value when deduct button is clicked
            teamBScore -= changeScore
            // Update the scoreboard for Team B
            updateTeamBScore()
        }

    }

    // Function to update the scoreboard for Team A in the app UI
    private fun updateTeamAScore(){
        /**To ensure that the score is within the range of 0 to 200 by using coerceIn() function.
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamAScore = teamAScore.coerceIn(0, 200)
        // Display the updated score for Team A
        scoreATextView.text = teamAScore.toString()
    }

    //Function to update the scoreboard for Team B in the app UI
    private fun updateTeamBScore(){
        /**To ensure that the score is within the range of 0 to 200 by using coerceIn() function.
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamBScore = teamBScore.coerceIn(0, 200)
        // Display the updated score for Team B
        scoreBTextView.text = teamBScore.toString()
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
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


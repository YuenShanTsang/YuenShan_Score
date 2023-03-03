package com.example.yuenshan_score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        /**To ensure that the score is within the range of 0 to 200 by using coerceIn() function
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamAScore = teamAScore.coerceIn(0, 200)
        // Display the updated score for Team A
        scoreATextView.text = teamAScore.toString()
    }

    //Function to update the scoreboard for Team B in the app UI
    private fun updateTeamBScore(){
        /**To ensure that the score is within the range of 0 to 200.
        It prevents the score from becoming negative or exceeding the maximum limit.**/
        teamBScore = teamBScore.coerceIn(0, 200)
        // Display the updated score for Team B
        scoreBTextView.text = teamBScore.toString()
    }

}
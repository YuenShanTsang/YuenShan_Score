package com.example.yuenshan_score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var addButtonA: Button
    private lateinit var deductButtonA: Button
    private lateinit var scoreATextView: TextView

    private lateinit var addButtonB: Button
    private lateinit var deductButtonB: Button
    private lateinit var scoreBTextView: TextView

    private lateinit var selectPoint: RadioGroup

    private var teamAScore = 0
    private var teamBScore = 0
    private var changeScore = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButtonA = findViewById(R.id.add_buttonA)
        deductButtonA = findViewById(R.id.deduct_buttonA)
        scoreATextView = findViewById(R.id.scoreA_text_view)

        addButtonB = findViewById(R.id.add_buttonB)
        deductButtonB = findViewById(R.id.deduct_buttonB)
        scoreBTextView = findViewById(R.id.scoreB_text_view)

        selectPoint = findViewById(R.id.radioGroup)

        selectPoint.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio1 -> changeScore = 1
                R.id.radio2 -> changeScore = 2
                R.id.radio3 -> changeScore = 3
            }
        }
        addButtonA.setOnClickListener{
            teamAScore += changeScore
            updateTeamAScore()
        }
        deductButtonA.setOnClickListener{
            teamAScore -= changeScore
            updateTeamAScore()
        }

        addButtonB.setOnClickListener{
            teamBScore += changeScore
            updateTeamBScore()
        }
        deductButtonB.setOnClickListener{
            teamBScore -= changeScore
            updateTeamBScore()
        }

    }

    private fun updateTeamAScore(){
        scoreATextView.text = teamAScore.toString()
    }
    private fun updateTeamBScore(){
        scoreBTextView.text = teamBScore.toString()
    }

}
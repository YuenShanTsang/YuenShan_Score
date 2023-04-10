package com.example.yuenshan_score

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast

class SettingActivity : AppCompatActivity() {


    private lateinit var saveScoreSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        saveScoreSwitch = findViewById(R.id.save_switch)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Set the initial state of the switch based on the user's preference
        saveScoreSwitch.isChecked = sharedPreferences.getBoolean("save_scores", false)

        // Set the listener for the switch
        saveScoreSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save the user's preference for saving scores
            sharedPreferences.edit().putBoolean("save_scores", isChecked).apply()


        }
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
                    val toast = Toast.makeText(
                        this,
                        "Developed by Yuen Shan Tsang (Clary)\nCourse Code: JAV1001",
                        Toast.LENGTH_SHORT
                    )
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
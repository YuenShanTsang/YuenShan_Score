package com.example.yuenshan_score

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.yuenshan_score.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    // Init view binding
    private lateinit var binding: ActivitySettingBinding

    private lateinit var sharedPreferences: SharedPreferences

    private val savePrefs = "SavePrefs"
    private val enabledSwitch = "EnabledSwitch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup view binding
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences(savePrefs, Context.MODE_PRIVATE)

        // Retrieve saved switch value
        val isEnabled = sharedPreferences.getBoolean(enabledSwitch, false)
        binding.saveSwitch.isChecked = isEnabled

        // Save switch value when user changes it
        binding.saveSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save switch value to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean(enabledSwitch, isChecked)
            editor.apply()

            // Show a Toast indicating the switch value
            val message = if (isChecked) "Scores will be saved" else "Scores will not be saved"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // Send the switch state to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isEnabled", isChecked)
            startActivity(intent)
        }
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
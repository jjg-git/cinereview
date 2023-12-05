package com.mobdeve.s15.gironasayasranario.cinereview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setTitle("Settings")

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
        }
    }

    // Handle the Up button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

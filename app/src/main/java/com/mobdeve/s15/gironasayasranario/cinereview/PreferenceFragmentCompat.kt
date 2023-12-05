package com.mobdeve.s15.gironasayasranario.cinereview

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)


        val notificationsPreference: Preference? = findPreference("notifications")
        notificationsPreference?.setOnPreferenceChangeListener { preference, newValue ->

            true
        }


        val signaturePreference: Preference? = findPreference("signature")
        signaturePreference?.setOnPreferenceChangeListener { preference, newValue ->

            true
        }


        val editProfilePreference: Preference? = findPreference("edit_profile")
        editProfilePreference?.setOnPreferenceClickListener { preference ->

            true
        }
        val userProfilePreference: Preference? = findPreference("user_profile")
        userProfilePreference?.setOnPreferenceClickListener {

            val intent = Intent(activity, UserProfileActivity::class.java)
            startActivity(intent)
            true
        }

        val logoutPreference: Preference? = findPreference("logout")
        logoutPreference?.setOnPreferenceClickListener { preference ->

            true
        }
    }
}

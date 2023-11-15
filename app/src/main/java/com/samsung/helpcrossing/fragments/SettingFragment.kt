// Copyright (c) 2023 Samsung Electronics Co. LTD. Released under the MIT License.

package com.samsung.helpcrossing.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.samsung.helpCrossing.R


class SettingFragment : PreferenceFragmentCompat() {
    private lateinit var sharedPreferences: SharedPreferences

    // key:fragment_setting
    private val keyThreshold = "threshold"
    private val keyObjects = "objects"
    private var objects: MultiSelectListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        objects = findPreference(keyObjects)
    }

    private fun summaryProvider(arg: String): Preference.SummaryProvider<*> {
        return Preference.SummaryProvider<EditTextPreference> { preference ->
            if (TextUtils.isEmpty(arg)) "not set"
            else "$arg"
        }
    }

    private val sharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                keyThreshold -> {
                    Toast.makeText(
                        context,
                        "value: ${sharedPreferences.getString(keyThreshold, "0.5")}",
                        Toast.LENGTH_SHORT
                    ).show()
                    CameraFragment.presetNum = 0
                }
            }
        }

    override fun onResume() {
        super.onResume()
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceListener)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceListener)
    }
}
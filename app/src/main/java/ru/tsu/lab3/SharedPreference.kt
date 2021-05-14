package ru.tsu.lab3

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "noteApp"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveInt(KEY_NAME: String, value: Int) {
        sharedPref
            .edit()
            .putInt(KEY_NAME, value)
            .apply()
    }
    fun getValueInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, -1)
    }

    fun saveValueString(KEY_NAME: String, value: String) {
        sharedPref
            .edit()
            .putString(KEY_NAME, value)
            .apply()
    }
    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, "")
    }

    fun clearSharedPreference() {
        sharedPref
            .edit()
            .clear()
            .apply()
    }

    fun removeValue(KEY_NAME: String) {
        sharedPref
            .edit()
            .remove(KEY_NAME)
            .apply()
    }
}

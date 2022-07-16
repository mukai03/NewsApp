package kg.geektech.newsapp

import android.content.Context
import android.text.Editable

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveState() {
        preferences.edit().putBoolean("isShown", true).apply()
    }

    fun isShown(): Boolean{
        return preferences.getBoolean("isShown", false)
    }

    fun saveNames(toString: String) {
        preferences.edit().putString("", "").apply()
    }

    fun saveNames(toString: String, name: String) {
        preferences.edit().putString("", name).apply()
    }
}
package kg.geektech.newsapp

import android.content.Context
import android.text.Editable
import java.security.AccessControlContext

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveState() {
        preferences.edit().putBoolean("isShown", true).apply()
    }

    fun isShown(): Boolean{
        return preferences.getBoolean("isShown", false)
    }

    fun afterTextChanged(p0: Editable?){
        preferences.edit().putString("name", p0.toString()).apply()
    }
}
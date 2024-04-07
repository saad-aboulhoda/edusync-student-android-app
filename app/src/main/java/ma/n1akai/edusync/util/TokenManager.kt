package ma.n1akai.edusync.util

import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.Preferences
import javax.inject.Inject

class TokenManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val KEY_ACCESS_TOKEN = "access_token"

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_ACCESS_TOKEN).apply()
    }
}
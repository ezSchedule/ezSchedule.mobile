package com.ezschedule.ezschedule.data.utils

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun saveToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun deleteToken() {
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    companion object {
        private const val PREFS_TOKEN_FILE = "prefs_token_file"
        private const val USER_TOKEN = "user_token"
    }
}
package com.ezschedule.ezschedule.presenter.utils

import android.content.Context
import android.util.Base64
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.Date

class TokenManager(context: Context) {
    private lateinit var splitToken: Array<String>
    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun decoded(jwtEncoded: String, behavior: (email: String) -> Unit) {
        try {
            splitToken =
                jwtEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val currentTimeStamp =
                (Date(System.currentTimeMillis()).time).toString().dropLast(3).toLong()

            if (currentTimeStamp > getExpirationFromJson()) behavior(getEmailFromJson())
        } catch (e: UnsupportedEncodingException) {
            //Error
        }
    }

    fun getToken() = prefs.getString(USER_TOKEN, null)

    private fun getJson(strEncoded: String) = String(Base64.decode(strEncoded, Base64.URL_SAFE))

    private fun getEmailFromJson() = JSONObject(getJson(splitToken[1])).getString("sub")

    private fun getExpirationFromJson() = JSONObject(getJson(splitToken[1])).getLong("exp")

    companion object {
        private const val PREFS_TOKEN_FILE = "prefs_token_file"
        private const val USER_TOKEN = "user_token"
    }
}
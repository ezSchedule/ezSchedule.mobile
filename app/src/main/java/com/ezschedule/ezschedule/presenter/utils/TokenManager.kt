package com.ezschedule.ezschedule.presenter.utils

import android.content.Context
import android.content.SharedPreferences
import com.ezschedule.network.domain.presentation.TenantPresentation

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun saveInfo(tenant: TenantPresentation) {
        editor.putInt(USER_ID, tenant.id)
        editor.putString(USER_EMAIL, tenant.email)
        editor.putString(USER_NAME, tenant.name)
        editor.putString(USER_IMAGE, tenant.image)
        editor.putString(USER_TOKEN, tenant.tokenJWT)
        editor.putInt(USER_ID_CONDOMINIUM, tenant.idCondominium)
        editor.apply()
    }

    fun getToken() = prefs.getString(USER_TOKEN, null)

    fun getInfo() = TenantPresentation(
        id = prefs.getInt(USER_ID, 0),
        email = prefs.getString(USER_EMAIL, null) ?: "",
        name = prefs.getString(USER_NAME, null) ?: "",
        image = prefs.getString(USER_IMAGE, null) ?: "",
        tokenJWT = prefs.getString(USER_TOKEN, null) ?: "",
        idCondominium = prefs.getInt(USER_ID_CONDOMINIUM, 0)
    )

    fun deleteInfo() {
        editor.remove(USER_TOKEN)
        editor.remove(USER_ID)
        editor.remove(USER_EMAIL)
        editor.remove(USER_NAME)
        editor.remove(USER_IMAGE)
        editor.remove(USER_ID_CONDOMINIUM)
        editor.apply()
    }

    companion object {
        private const val PREFS_TOKEN_FILE = "prefs_token_file"
        private const val USER_TOKEN = "user_token"
        private const val USER_ID = "user_id"
        private const val USER_EMAIL = "user_email"
        private const val USER_NAME = "user_name"
        private const val USER_IMAGE = "user_image"
        private const val USER_ID_CONDOMINIUM = "user_id_condominium"
    }
}
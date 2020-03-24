package com.sakshi.coolshopapplication.arch

import android.app.Activity
import android.content.Context

class SharedPref {

    companion object {
        private const val NAME_SHARED_PREF = "cool_shop_shared_pref"
        private const val AUTH_TOKEN = "auth_token"
        private const val USER_ID = "user_id"
        private const val EMAIL_ID = "email_id"
        private const val PASSWORD = "password"
        private const val IMAGE = "image"

        fun saveAuthToken(activity: Activity, token: String) {
            val sharedPref = activity.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
            sharedPref.edit().putString(AUTH_TOKEN, token).apply()
        }

        fun getAuthToken(activity: Activity): String? = activity.getSharedPreferences(
            NAME_SHARED_PREF, Context.MODE_PRIVATE
        ).getString(AUTH_TOKEN, "")

        fun saveUserId(activity: Activity, userId: String) {
            val sharedPref = activity.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
            sharedPref.edit().putString(USER_ID, userId).apply()
        }

        fun getUserId(activity: Activity): String? = activity.getSharedPreferences(
            NAME_SHARED_PREF, Context.MODE_PRIVATE
        ).getString(USER_ID, "")

        fun saveEmail(activity: Activity, email: String) {
            val sharedPref = activity.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
            sharedPref.edit().putString(EMAIL_ID, email).apply()
        }

        fun getEmailId(activity: Activity): String? = activity.getSharedPreferences(
            NAME_SHARED_PREF, Context.MODE_PRIVATE
        ).getString(EMAIL_ID, "")

        fun savePassword(activity: Activity, password: String) {
            val sharedPref = activity.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(PASSWORD, password).apply()
            }
        }

        fun getPassword(activity: Activity): String? = activity.getSharedPreferences(
            NAME_SHARED_PREF, Context.MODE_PRIVATE
        ).getString(PASSWORD, "")

        fun saveImage(activity: Activity, image: String) {
            val sharedPref = activity.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(IMAGE, image).apply()
            }
        }

        fun getImage(activity: Activity): String? = activity.getSharedPreferences(
            NAME_SHARED_PREF, Context.MODE_PRIVATE
        ).getString(IMAGE, "")
    }
}
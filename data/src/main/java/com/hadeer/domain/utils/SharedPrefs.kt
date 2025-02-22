package com.hadeer.domain.utils

import android.content.Context

data object SharedPrefs {
    private const val PREF_APP = "app_prefs"

    fun saveStringData(context : Context, key : String, value: String?) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key,value!!).apply()
    }

    fun saveIntData(context: Context, key: String, value: Int?){
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, value!!).apply()
    }

    fun getStringData(context: Context, key: String) : String? {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, null)
    }

    fun getIntData(context: Context, key: String) : Int? {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key,0)
    }
}
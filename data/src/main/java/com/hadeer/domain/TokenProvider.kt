package com.hadeer.domain

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.hadeer.domain.utils.SecurSharedPrefs
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.M)
class TokenProvider @Inject constructor(
    private val context : Context
){
    fun getToken() = SecurSharedPrefs.getSharedPreferences(context).getString("access_token", "")
    fun setToken(token : String) = SecurSharedPrefs.getSharedPreferences(context).edit().putString("access_token", token).apply()
}
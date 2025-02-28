package com.hadeer.domain.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
@RequiresApi(Build.VERSION_CODES.M)
object SecurSharedPrefs {
    private const val PREFS_FILE_NAME = "secure_prefs"

    fun getSharedPreferences(context : Context):SharedPreferences{
        val masterKeyAlias = MasterKeys.getOrCreate(
            MasterKeys.AES256_GCM_SPEC
        )
        return EncryptedSharedPreferences.create(
            PREFS_FILE_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
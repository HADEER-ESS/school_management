package com.hadeer.domain

import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.M)
class TokenProvider @Inject constructor(
){
    fun getToken() = SecureCryptoManager().decryptToken()
    fun setToken(token : String) = SecureCryptoManager().encryptToken(token)
}
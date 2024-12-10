package com.hadeer.domain

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@RequiresApi(Build.VERSION_CODES.M)
class SecureCryptoManager {

    private lateinit var secureSharedPreferance : EncryptedSharedPreferences

    private val keystore = KeyStore.getInstance("AndroidKeyStore")
        .apply {
            load(null)
        }

    companion object{
        private const val ALIAS = "appSecret"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC

        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    private fun getKey() : SecretKey{
        val isExist = keystore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return isExist?.secretKey ?: generateKey()
    }

    private fun generateKey(): SecretKey{
        val secretKey = KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }
        return secretKey.generateKey()
    }

    private val encryptCipher = Cipher.getInstance(TRANSFORMATION)
        .apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private val decryptCipher = Cipher.getInstance(TRANSFORMATION)
        .apply {
            init(Cipher.DECRYPT_MODE, getKey())
        }

    // Encrypt and persist the token
    fun encryptToken(token :String){
        val encryptedBytes = encryptCipher.doFinal(token.toByteArray())
        val encryptedToken = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        secureSharedPreferance.edit().putString(ALIAS, encryptedToken).apply()
    }

    fun decryptToken():String?{
        val encryptedToken = secureSharedPreferance.getString(ALIAS, null) ?: return null
        val decodeByte = Base64.decode(encryptedToken, Base64.DEFAULT)
        val decryptedByte = decryptCipher.doFinal(decodeByte)
        return String(decryptedByte)
    }
}
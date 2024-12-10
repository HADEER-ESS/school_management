package com.hadeer.schoolapp.di

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.hadeer.domain.AuthorizationToken
import com.hadeer.domain.TokenProvider
import com.hadeer.schoolapp.ui.AuthActivity
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
class TokenExpiredInterceptor @Inject constructor(
    private val context : Context,
    private val tokenProvider: TokenProvider
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val invocation = originalRequest.tag(Invocation::class.java)

        val authorizationToken = invocation?.method()?.isAnnotationPresent(AuthorizationToken::class.java) ?: false

        if(!authorizationToken){
            val response = chain.proceed(originalRequest)
            return response
        }
        else{
            val token = tokenProvider.getToken()
            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            val newResponse = chain.proceed(newRequest)
            if(newResponse.code == 401){ //unauthorized
                tokenProvider.setToken("")
                handleUnAuthorizedToken()
            }
            return newResponse
        }
    }

    private fun handleUnAuthorizedToken() {
        val intent = Intent(context, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        if(context is Activity){
            context.finish()
        }
        else{
            context.startActivity(intent)
        }
    }
}
package com.hadeer.schoolapp.di

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadeer.domain.ApiService
import com.hadeer.domain.TokenProvider
import com.hadeer.schoolapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.M)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModal {
    @Provides
    @Singleton
    fun provideTokenProvider() : TokenProvider{
        return TokenProvider()
    }

    @Singleton
    @Provides
    fun provideGson():Gson{
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor() : HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideTokenExpiration(
        @ApplicationContext context: Context,
        tokenProvider: TokenProvider
    ) : TokenExpiredInterceptor {
        return TokenExpiredInterceptor(context, tokenProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttp() :OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient : OkHttpClient, gson:Gson):Retrofit{
        Log.d("BuildConfig", "API_URL: ${BuildConfig.API_URL}")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}
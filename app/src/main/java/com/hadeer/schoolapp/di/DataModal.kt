package com.hadeer.schoolapp.di

import android.content.Context
import com.hadeer.domain.ApiService
import com.hadeer.domain.repo.LoginRepo
import com.hadeer.domain.repoImpl.LoginRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModal {
    @Provides
    @Singleton
     fun provideLogin(apiService : ApiService, @ApplicationContext context:Context) : LoginRepo = LoginRepoImpl(
        apiService,
        context
    )
}
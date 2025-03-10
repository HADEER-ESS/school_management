package com.hadeer.domain

import com.hadeer.domain.entities.home.announcements.AnnouncementResponse
import com.hadeer.domain.entities.home.events.EventResponse
import com.hadeer.domain.entities.login.LoginBody
import com.hadeer.domain.entities.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginBody : LoginBody) : Response<LoginResponse>

    @AuthorizationToken
    @GET("events")
    suspend fun getEvents():Response<EventResponse>

    @AuthorizationToken
    @GET("announcements/search")
    suspend fun getAnnouncements():Response<AnnouncementResponse>
}
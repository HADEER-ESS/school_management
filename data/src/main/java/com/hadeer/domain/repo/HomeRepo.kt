package com.hadeer.domain.repo

import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.home.announcements.AnnouncementResponseModel
import com.hadeer.domain.entities.home.events.EventResponseModal

interface HomeRepo {
    suspend fun getEvents() : NetworkResponse<List<EventResponseModal>>
    suspend fun getAnnouncement() : NetworkResponse<List<AnnouncementResponseModel>>
}
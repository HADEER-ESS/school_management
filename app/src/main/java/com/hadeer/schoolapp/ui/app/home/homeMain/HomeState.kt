package com.hadeer.schoolapp.ui.app.home.homeMain

import com.hadeer.domain.entities.home.announcements.AnnouncementResponseModel
import com.hadeer.domain.entities.home.events.EventResponseModal

data class HomeState(
    val events_isLoading : Boolean = false,
    val announcement_isLoading : Boolean = false,
    val eventsData : List<EventResponseModal> = emptyList(),
    val announcementData: List<AnnouncementResponseModel> = emptyList(),
    val events_success : Boolean = false,
    val announcement_success : Boolean = false,
    val events_error_message : String = "",
    val announcement_error_message : String = "",
)

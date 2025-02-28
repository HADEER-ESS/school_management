package com.hadeer.schoolapp.ui.app.home.homeMain

import com.hadeer.domain.entities.home.events.EventResponseModal

data class HomeState(
    val isLoading : Boolean = false,
    val eventsData : List<EventResponseModal> = emptyList(),
    val events_success : Boolean = false,
    val announcement_success : Boolean = false,
    val events_error_message : String = "",
    val announcement_error_message : String = "",
)

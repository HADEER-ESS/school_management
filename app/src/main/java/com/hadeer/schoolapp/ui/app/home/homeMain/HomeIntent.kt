package com.hadeer.schoolapp.ui.app.home.homeMain

sealed class HomeIntent {
    data class Idleal (val state: HomeState) : HomeIntent()
    data class Event_Success(val state: HomeState) : HomeIntent()
    data class Event_Failed(val state: HomeState) : HomeIntent()
    data class Announcement_Success(val state: HomeState) : HomeIntent()
    data class Announcement_Failed(val state: HomeState) : HomeIntent()
}
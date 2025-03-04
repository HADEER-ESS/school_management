package com.hadeer.domain.entities.home.announcements

data class AnnouncementResponseModel(
    val id : Int,
    val author : String,
    val details : String,
    val viewsNumber : Int,
    val date : String
)

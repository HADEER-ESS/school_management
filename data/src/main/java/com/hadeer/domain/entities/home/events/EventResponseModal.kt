package com.hadeer.domain.entities.home.events

import android.net.Uri

data class EventResponseModal(
    val id : Int,
    val name : String,
    val desc : String,
    val image : String?,
    val date : String,
    val place : String
)

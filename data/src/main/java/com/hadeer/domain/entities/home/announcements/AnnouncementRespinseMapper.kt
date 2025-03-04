package com.hadeer.domain.entities.home.announcements

internal fun AnnouncementDataItem.getAnnouncement() : AnnouncementResponseModel{
    return AnnouncementResponseModel(
        id = id?: 0,
        author = authorType?:"",
        details = description?: "",
        viewsNumber = views?: 0,
        date = createdAt ?: ""
    )
}
package com.hadeer.domain.entities.home.events

internal fun EventsDataItem.toGetItemData() : EventResponseModal{
    return EventResponseModal(
        id = id?: 0,
        name = name ?: "",
        desc = description  ?: "",
        image = (image?: "").toString(),
        date = startDate ?: "",
        place = city?.name ?: ""
    )
}
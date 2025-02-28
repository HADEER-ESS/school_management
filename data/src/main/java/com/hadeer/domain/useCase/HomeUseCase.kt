package com.hadeer.domain.useCase

import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.home.events.EventResponseModal
import com.hadeer.domain.repo.HomeRepo
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {
    suspend fun getEventsData()  : NetworkResponse<List<EventResponseModal>> = homeRepo.getEvents()

}
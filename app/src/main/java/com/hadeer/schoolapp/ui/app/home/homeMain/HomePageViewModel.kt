package com.hadeer.schoolapp.ui.app.home.homeMain

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.useCase.HomeUseCase
import com.hadeer.schoolapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeUseCase : HomeUseCase,
    private val context: Application
): ViewModel(){
    var currentState = HomeState()
    private val _homeIntent = MutableSharedFlow<HomeIntent>()
    var homeIntent = _homeIntent
        .asSharedFlow()

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            getAnnouncementData()
            getHomeEventsData()
        }
    }

    fun getHomeEventsData(){
        viewModelScope.launch {
            currentState = currentState.copy(
                events_isLoading = true
            )
            _homeIntent.emit(
                HomeIntent.Idleal(currentState)
            )
            val response = homeUseCase.getEventsData()
            when(response){
                is NetworkResponse.Success -> {
                    currentState = currentState.copy(
                        events_isLoading = false,
                        eventsData = response.body,
                        events_success = true
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Success(currentState)
                    )
                }
                is NetworkResponse.ApiError -> {
                    currentState = currentState.copy(
                        events_isLoading = false,
                        events_error_message = response.body,
                        events_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Failed(currentState)
                    )
                }
                is NetworkResponse.NetworkError -> {
                    currentState = currentState.copy(
                        events_isLoading = false,
                        events_error_message = context.getString(R.string.network_error),
                        events_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Failed(currentState)
                    )
                }
                is NetworkResponse.UnknowError -> {
                    currentState = currentState.copy(
                        events_isLoading = false,
                        events_error_message = context.getString(R.string.unknow_error),
                        events_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Failed(currentState)
                    )
                }
            }
        }
    }

    fun getAnnouncementData(){
        viewModelScope.launch {
            currentState = currentState.copy(
                announcement_isLoading = true
            )
            _homeIntent.emit(
                HomeIntent.Idleal(currentState)
            )
            val response = homeUseCase.getAnnouncementData()

            when(response){
                is NetworkResponse.Success -> {
                    currentState = currentState.copy(
                        announcement_isLoading = false,
                        announcementData = response.body,
                        announcement_success = true
                    )
                    _homeIntent.emit(
                        HomeIntent.Announcement_Success(currentState)
                    )
                }
                is NetworkResponse.ApiError -> {
                    currentState = currentState.copy(
                        announcement_isLoading = false,
                        announcement_error_message = response.body,
                        announcement_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Announcement_Failed(currentState)
                    )
                }
                is NetworkResponse.NetworkError -> {
                    currentState = currentState.copy(
                        announcement_isLoading = false,
                        announcement_error_message = context.getString(R.string.network_error),
                        announcement_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Announcement_Failed(currentState)
                    )
                }
                is NetworkResponse.UnknowError -> {
                    currentState = currentState.copy(
                        announcement_isLoading = false,
                        announcement_error_message = context.getString(R.string.unknow_error),
                        announcement_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Announcement_Failed(currentState)
                    )
                }
            }

        }
    }
}
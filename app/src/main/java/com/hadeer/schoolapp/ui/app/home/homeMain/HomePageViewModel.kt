package com.hadeer.schoolapp.ui.app.home.homeMain

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.useCase.HomeUseCase
import com.hadeer.schoolapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeUseCase : HomeUseCase,
    private val context: Application
): ViewModel(){
    var currentState = HomeState()
    private val _homeIntent = MutableSharedFlow<HomeIntent>()
    var homeIntent = _homeIntent.asSharedFlow()
    init {
        getHomeEventsData()
    }
    fun getHomeEventsData(){
        println("response ........")
        viewModelScope.launch {
            currentState = currentState.copy(
                isLoading = true
            )
            _homeIntent.emit(
                HomeIntent.Idleal(currentState)
            )
            val response = homeUseCase.getEventsData()
            when(response){
                is NetworkResponse.Success -> {
                    currentState = currentState.copy(
                        isLoading = false,
                        eventsData = response.body,
                        events_success = true
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Success(currentState)
                    )
                }
                is NetworkResponse.ApiError -> {
                    currentState = currentState.copy(
                        isLoading = false,
                        events_error_message = response.body,
                        events_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Failed(currentState)
                    )
                }
                is NetworkResponse.NetworkError -> {
                    currentState = currentState.copy(
                        isLoading = false,
                        events_error_message = context.getString(R.string.network_error),
                        events_success = false
                    )
                    _homeIntent.emit(
                        HomeIntent.Event_Failed(currentState)
                    )
                }
                is NetworkResponse.UnknowError -> {
                    currentState = currentState.copy(
                        isLoading = false,
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
}
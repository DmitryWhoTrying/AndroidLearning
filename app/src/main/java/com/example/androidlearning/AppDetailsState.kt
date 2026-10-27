package com.example.androidlearning

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(val appData: List<AppData>) : AppDetailsState
}
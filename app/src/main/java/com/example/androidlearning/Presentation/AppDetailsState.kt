package com.example.androidlearning.Presentation

import com.example.androidlearning.Domain.AppData

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Content(val appData: List<AppData>) : AppDetailsState
}
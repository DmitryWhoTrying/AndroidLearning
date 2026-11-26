package com.example.androidlearning.Presentation

import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.model.AppDetails

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data object Error : AppDetailsState
    data class Content(
        val appData: List<AppData> = emptyList(),
        val installState: InstallState = InstallState.NotInstalled,
        val selectedApp: AppData? = null,
        val ratingsState: RatingsState = RatingsState.Loading,
        val appDetails: AppDetails? = null,
        val descriptionCollapsed: Boolean = false,
    ) : AppDetailsState
}

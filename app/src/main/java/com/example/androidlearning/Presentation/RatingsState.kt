package com.example.androidlearning.Presentation

sealed interface RatingsState {
    data object Loading : RatingsState
    data class Content(val ratings: Map<String, Float>) : RatingsState
}
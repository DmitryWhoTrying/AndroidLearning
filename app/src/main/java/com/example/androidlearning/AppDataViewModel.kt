package com.example.androidlearning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds


sealed interface ScreenEvent{
    data class ShowSnackbar(val message: String) : ScreenEvent
}
class AppDataViewModel(repositoty: AppDataRepositoty = AppDataRepositoty()) : ViewModel() {
    private val _repositoty = repositoty
    private val _state = MutableStateFlow<AppDetailsState>(
        AppDetailsState.Loading
    )
    val state = _state.asStateFlow()


    private val _events = Channel<ScreenEvent>(
        Channel.BUFFERED,
        )
    val events = _events.receiveAsFlow()

    fun processToolbarClick(){
        viewModelScope.launch {
            _events.send(
                ScreenEvent.ShowSnackbar("🍰🍰🍰")
            )
        }
    }

    init {
        getData()
    }

    fun getData(){
        viewModelScope.launch {
            runCatching {
                //hard work imitation
                delay(3.seconds)

                _state.value = AppDetailsState.Content(
                    appData = _repositoty.getData()
                )
            }.onFailure { e ->
                if (e is CancellationException) throw e   // не глотаем отмену
                // здесь можно завести AppDetailsState.Error(e.message ?: "Unknown")
            }
        }
    }

}
package com.example.androidlearning.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.Domain.AppDataRepository
import com.example.androidlearning.Domain.GetAppDataUseCase
import com.example.androidlearning.Domain.GetAppDataUseCaseImpl
import com.example.androidlearning.Domain.GetAppRatingUseCase
import com.example.androidlearning.Domain.GetAppRatingUseCaseImpl
import com.example.androidlearning.Domain.GetFullAppDataUseCase
import com.example.androidlearning.Domain.GetFullAppDataUseCaseImpl
import com.example.androidlearning.Presentation.AppDetailsState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.log
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds


sealed interface ScreenEvent{
    data class ShowSnackbar(val message: String) : ScreenEvent
}

@HiltViewModel
class AppDataViewModel @Inject constructor(private val repository: AppDataRepository ) : ViewModel() {
    private val _repository = repository

    private val getAppDataUseCase: GetAppDataUseCase = GetAppDataUseCaseImpl(
        repository=_repository
    )
    private val getAppDataFullUseCase: GetFullAppDataUseCase = GetFullAppDataUseCaseImpl(
        repository = _repository
    )
    private val getAppRatingUseCase: GetAppRatingUseCase = GetAppRatingUseCaseImpl(
        repository = _repository
    )

    private val _state = MutableStateFlow<AppDetailsState>(
        AppDetailsState.Loading
    )
    private var installJob: Job? = null
    val state = _state.asStateFlow()

    init {
        getData()
    }


    fun onInstallClick() {
        val current = _state.value
        if (current !is Content) return

        if (current.installState is InstallState.Installing) {
            installJob?.cancel()
            _state.update { state ->
                if (state is Content) state.copy(installState = InstallState.NotInstalled) else state
            }
        } else {
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        try {
            for (progress in 0..100 step 5) {
                _state.update { current ->
                    if (current is Content) {
                        current.copy(installState = InstallState.Installing(progress))
                    } else current
                }
                delay(150)
            }
            _state.update { current ->
                if (current is Content) {
                    current.copy(installState = InstallState.Installed)
                } else current
            }
        } catch (e: CancellationException) {
            _state.update { current ->
                if (current is Content) {
                    current.copy(installState = InstallState.NotInstalled)
                } else current
            }
            throw e
        }
    }

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
    fun getData(){
        viewModelScope.launch {
            runCatching {
                val apps = getAppDataUseCase()
                _state.value = Content(
                    appData = apps,
                    installState = InstallState.NotInstalled,
                    ratingsState = RatingsState.Loading
                )
                getRatings(apps.mapNotNull { it.id })
            }.onFailure { e ->
                if (e is CancellationException) throw e   // не глотаем отмену
                // здесь можно завести AppDetailsState.Error(e.message ?: "Unknown")
            }
        }
    }

    fun getFullData(appId: String) {
        viewModelScope.launch {
            runCatching {
                val fullData = getAppDataFullUseCase(appId)
                _state.update { current ->
                    if (current is Content) {
                        val rating = (current.ratingsState as? RatingsState.Content)?.ratings?.get(appId)
                            ?: current.selectedApp?.rating
                            ?: current.appData.find { it.id == appId }?.rating
                        current.copy(selectedApp = fullData.copy(rating = rating))
                    } else current
                }
            }.onFailure { e ->
                if (e is CancellationException) throw e
            }
        }
    }

    fun onAppClick(appId: String) {
        val current = _state.value
        if (current is Content) {
            val rating = (current.ratingsState as? RatingsState.Content)?.ratings?.get(appId)
            val selected = current.appData.find { it.id == appId }
                ?.let { if (rating != null) it.copy(rating = rating) else it }
                ?: AppData(id = appId, appName = null, appGenre = null, appDescription = null, imagePath = null, rating = rating)
            _state.update {
                current.copy(selectedApp = selected)
            }
        }
        getFullData(appId)
    }

    fun onBackClick() {
        _state.update { current ->
            if (current is Content) {
                current.copy(selectedApp = null)
            } else current
        }
    }

    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        ids.forEach { id ->
            val rating = getAppRatingUseCase(id)
            ratings[id] = rating
        }
        return ratings
    }

    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> =
        coroutineScope {
        ids.map { id ->
            async { id to getAppRatingUseCase(id)}
        }.awaitAll().toMap()
    }

    fun getRatings(ids: List<String>) {
        viewModelScope.launch {
            lateinit var ratings : Map<String, Float>
            val timeMillis = measureTimeMillis {
                ratings = loadRatingsInParallel(ids)
            }
            println("Ratings loaded in $timeMillis ms")
            _state.update { current ->
                if (current is Content) {
                    val updatedAppData = current.appData.map { app ->
                        app.copy(rating = ratings[app.id] ?: app.rating)
                    }
                    val updatedSelectedApp = current.selectedApp?.let { selected ->
                        selected.id?.let { id ->
                            selected.copy(rating = ratings[id] ?: selected.rating)
                        } ?: selected
                    }
                    current.copy(
                        appData = updatedAppData,
                        selectedApp = updatedSelectedApp,
                        ratingsState = RatingsState.Content(ratings)
                    )
                } else current
            }

        }
    }
}
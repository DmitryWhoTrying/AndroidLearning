package com.example.androidlearning.Data

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class AppDataAPI {
    suspend fun get(): List<AppDataDTO>{
        delay(1500.milliseconds)
        return listOf(AppDataDTO(
            "Game 1",
            "Strategy",
            "Description 1, pretty small",
            "https://picsum.photos/200/200",
            4.5,
            4.5
        ),
        AppDataDTO(
            "Game 2", "Economical", "Description 2, pretty big pretty" +
                    " big pretty big pretty " + " big pretty big pretty " +
                    "big pretty big pretty big", "https://picsum.photos/200/200",4.5,
            4.5
        ),
        AppDataDTO("Game 3", "Adventure", "Decription 3", "https://picsum.photos/200/200", 4.5,
            4.5),
        AppDataDTO("Game 4", "Action", "Description 4", "https://picsum.photos/200/200", 4.5,
            4.5),
        AppDataDTO("Game 5", "Strategy", null, "https://picsum.photos/200/200", 4.5,
            4.5),
        AppDataDTO("Game 6", null, "Description 6", "https://picsum.photos/200/200", 4.5,
            4.5),
        AppDataDTO("Game 7", "Strategy", "Description 7", "https://picsum.photos/200/200", 4.5,
            4.5),
        AppDataDTO("Game 8", "Strategy", "Description 8", "https://picsum.photos/200/200", 4.5,
            4.5))
    }
}
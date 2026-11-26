package com.example.androidlearning

import com.example.androidlearning.Data.API.AppDataCompact
import com.example.androidlearning.Data.API.AppDataFull
import com.example.androidlearning.Data.AppDataDTO
import com.example.androidlearning.Data.AppDataMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MappersUnitTest {

    @Test
    fun map_AppDataDTO_withValidData_returnsCorrectAppData() {
        val dto = AppDataDTO(
            appName = "Calculator",
            appGenre = "Tools",
            appDescription = "Simple calculator app",
            imagePath = "https://example.com/calc.png",
            appSize = 5.2,
            appRating = 4.8
        )

        val result = AppDataMapper.map(dto)

        assertNull(result.id)
        assertEquals("Calculator", result.appName)
        assertEquals("Tools", result.appGenre)
        assertEquals("Simple calculator app", result.appDescription)
        assertEquals("https://example.com/calc.png", result.imagePath)
        assertNull(result.screenshots)
    }

    @Test
    fun map_AppDataDTO_withNullableFields_returnsAppDataWithNulls() {
        val dto = AppDataDTO(
            appName = "Minimal App",
            appGenre = null,
            appDescription = null,
            imagePath = null,
            appSize = 1.0,
            appRating = 3.5
        )

        val result = AppDataMapper.map(dto)

        assertNull(result.id)
        assertEquals("Minimal App", result.appName)
        assertNull(result.appGenre)
        assertNull(result.appDescription)
        assertNull(result.imagePath)
        assertNull(result.screenshots)
    }

    @Test
    fun map_AppDataCompact_withValidData_returnsCorrectAppData() {
        val compact = AppDataCompact().apply {
            id = "compact-123"
            name = "Notes"
            category = "Productivity"
            description = "Quick note taking app"
            iconUrl = "https://example.com/notes.png"
        }

        val result = AppDataMapper.map(compact)

        assertEquals("compact-123", result.id)
        assertEquals("Notes", result.appName)
        assertEquals("Productivity", result.appGenre)
        assertEquals("Quick note taking app", result.appDescription)
        assertEquals("https://example.com/notes.png", result.imagePath)
        assertNull(result.screenshots)
    }

    @Test
    fun map_AppDataCompact_withNullFields_returnsAppDataWithNulls() {
        val compact = AppDataCompact()

        val result = AppDataMapper.map(compact)

        assertNull(result.id)
        assertNull(result.appName)
        assertNull(result.appGenre)
        assertNull(result.appDescription)
        assertNull(result.imagePath)
        assertNull(result.screenshots)
    }

    @Test
    fun map_AppDataFull_withValidData_returnsCorrectAppData() {
        val full = AppDataFull().apply {
            id = "full-456"
            name = "Hero Game"
            developer = "SuperDev"
            category = "RPG"
            ageRating = 12
            size = 250.5
            iconUrl = "https://example.com/game_icon.png"
            screenshotUrlList = listOf("https://example.com/s1.png", "https://example.com/s2.png")
            description = "Epic action RPG game"
        }

        val result = AppDataMapper.map(full)

        assertEquals("full-456", result.id)
        assertEquals("Hero Game", result.appName)
        assertEquals("RPG", result.appGenre)
        assertEquals("Epic action RPG game", result.appDescription)
        assertEquals("https://example.com/game_icon.png", result.imagePath)
        assertEquals(listOf("https://example.com/s1.png", "https://example.com/s2.png"), result.screenshots)
        assertEquals(listOf("https://example.com/s1.png", "https://example.com/s2.png"), result.screenshotUrlList)
        assertEquals(12, result.appAgeRating)
        assertEquals(250.5, result.appSize ?: 0.0, 0.001)
    }

    @Test
    fun map_AppDataFull_withNullFields_returnsAppDataWithNulls() {
        val full = AppDataFull()

        val result = AppDataMapper.map(full)

        assertNull(result.id)
        assertNull(result.appName)
        assertNull(result.appGenre)
        assertNull(result.appDescription)
        assertNull(result.imagePath)
        assertNull(result.screenshots)
        assertNull(result.screenshotUrlList)
        assertNull(result.appAgeRating)
        assertNull(result.appSize)
    }
}

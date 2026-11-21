package com.example.androidlearning

import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.Domain.AppDataRepository
import com.example.androidlearning.Domain.GetAppDataUseCaseImpl
import com.example.androidlearning.Domain.GetAppRatingUseCaseImpl
import com.example.androidlearning.Domain.GetFullAppDataUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UseCasesUnitTest {

    @Test
    fun getAppDataUseCase_returnsAppList_andVerifiesRepositoryCall() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            val expectedList = listOf(
                AppData(id = "1", appName = "App 1", appGenre = "Tools", appDescription = "Desc 1", imagePath = "icon1.png")
            )
            whenever(repository.get()).doReturn(expectedList)

            val useCase = GetAppDataUseCaseImpl(repository)
            val result = useCase()

            assertNotNull(result)
            assertEquals(1, result.size)
            assertEquals("App 1", result[0].appName)
            verify(repository).get()
        }
    }

    @Test
    fun getAppDataUseCase_whenRepositoryIsEmpty_returnsEmptyList() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            whenever(repository.get()).doReturn(emptyList())

            val useCase = GetAppDataUseCaseImpl(repository)
            val result = useCase()

            assertNotNull(result)
            assertTrue(result.isEmpty())
            verify(repository).get()
        }
    }

    @Test
    fun getFullAppDataUseCase_withValidId_returnsFullAppData() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            val expectedFullApp = AppData(
                id = "app-100",
                appName = "Full App",
                appGenre = "Games",
                appDescription = "Full description",
                imagePath = "full_icon.png",
                screenshots = listOf("s1.png", "s2.png"),
                appSize = 120.0,
                appAgeRating = 16
            )
            whenever(repository.getAppFull("app-100")).doReturn(expectedFullApp)

            val useCase = GetFullAppDataUseCaseImpl(repository)
            val result = useCase("app-100")

            assertEquals("app-100", result.id)
            assertEquals("Full App", result.appName)
            assertEquals(16, result.appAgeRating)
            assertEquals(120.0, result.appSize ?: 0.0, 0.001)
            verify(repository).getAppFull("app-100")
        }
    }

    @Test
    fun getFullAppDataUseCase_withAnotherId_callsRepositoryWithCorrectId() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            val expectedApp = AppData(
                id = "app-999",
                appName = "Special App",
                appGenre = "Utility",
                appDescription = null,
                imagePath = null
            )
            whenever(repository.getAppFull("app-999")).doReturn(expectedApp)

            val useCase = GetFullAppDataUseCaseImpl(repository)
            val result = useCase("app-999")

            assertEquals("app-999", result.id)
            assertEquals("Special App", result.appName)
            verify(repository).getAppFull("app-999")
        }
    }

    @Test
    fun getAppRatingUseCase_returnsRatingForValidAppId() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            whenever(repository.getRating("app-777")).doReturn(4.5f)

            val useCase = GetAppRatingUseCaseImpl(repository)
            val result = useCase("app-777")

            assertEquals(4.5f, result, 0.001f)
            verify(repository).getRating("app-777")
        }
    }

    @Test
    fun getAppRatingUseCase_returnsDifferentRatingForAnotherAppId() {
        runBlocking {
            val repository = mock<AppDataRepository>()
            whenever(repository.getRating("app-123")).doReturn(3.8f)

            val useCase = GetAppRatingUseCaseImpl(repository)
            val result = useCase("app-123")

            assertEquals(3.8f, result, 0.001f)
            verify(repository).getRating("app-123")
        }
    }
}

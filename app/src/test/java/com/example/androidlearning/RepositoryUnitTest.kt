package com.example.androidlearning

import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.Domain.AppDataRepository
import com.example.androidlearning.Domain.GetAppDataUseCaseImpl
import com.example.androidlearning.Domain.GetAppRatingUseCaseImpl
import com.example.androidlearning.Domain.GetFullAppDataUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RepositoryUnitTest {

    @Test
    fun getAppsNotEmpty() {
        runBlocking {
            val mockRepository = mock<AppDataRepository>()

            // 2. Define mock returns / data
            val mockApps = listOf(
                AppData(
                    id = "app_1",
                    appName = "Game App",
                    appGenre = "Action",
                    appDescription = "Fun action game",
                    imagePath = "https://example.com/icon.jpg"
                )
            )

            whenever(mockRepository.get()).doReturn(mockApps)

            val getAppDataUseCase = GetAppDataUseCaseImpl(mockRepository)
            val result = getAppDataUseCase()

            assertNotNull(result)
            //assertEquals(1, result.size)
            //assertEquals("Game App", result[0].appName)

            verify(mockRepository).get()
        }
    }

    @Test
    fun getAppsEmpty(){
        runBlocking {
            val mockRepository = mock<AppDataRepository>()

            whenever(mockRepository.get()).doReturn(listOf())

            val getAppDataUseCase = GetAppDataUseCaseImpl(mockRepository)
            val result = getAppDataUseCase()

            assertEquals(0, result.size)

            verify(mockRepository).get()
        }
    }


    @Test
    fun getFullAppDataReturnNotNull(){
        runBlocking {
            val appId = "AppId";
            val mockRepository = mock<AppDataRepository>()

            val mockApp = AppData(
                id = appId,
                appName = "Game App",
                appGenre = "Action",
                appDescription = "Fun action game",
                imagePath = "https://example.com/icon.jpg"
            )

            whenever(mockRepository.getAppFull(appId)).doReturn(mockApp)

            val getFullAppDataUseCase = GetFullAppDataUseCaseImpl(mockRepository)
            val result = getFullAppDataUseCase(appId)

            assertEquals(appId, result.id)

            verify(mockRepository).getAppFull(appId)
        }
    }

    @Test
    fun getFullAppDataReturnsEmptyObj(){
        runBlocking {
            val appId = "AppId";
            val mockRepository = mock<AppDataRepository>()

            val emptyApp = AppData(
                id = null,
                appName = null,
                appGenre = null,
                appDescription = null,
                imagePath = null
            )

            whenever(mockRepository.getAppFull(appId)).doReturn(emptyApp)

            val getFullAppDataUseCase = GetFullAppDataUseCaseImpl(mockRepository)
            val result = getFullAppDataUseCase(appId)

            assertNotNull(result)

            verify(mockRepository).getAppFull(appId)
        }
    }

    @Test
    fun getRatingStubTest(){
        runBlocking {
            val appId = "AppId";
            val mockRepository = mock<AppDataRepository>()

            whenever(mockRepository.getRating(appId)).doReturn(5.5.toFloat())

            val getAppRatingUseCase = GetAppRatingUseCaseImpl(mockRepository)
            val result = getAppRatingUseCase(appId)

            assert(result.equals(5.5.toFloat()))

            verify(mockRepository).getRating(appId)
        }
    }
}

package com.example.androidlearning.Data

import com.example.androidlearning.Data.API.AppDataCompact
import com.example.androidlearning.Data.API.AppDataFull
import com.example.androidlearning.Domain.AppData

class AppDataMapper {
    companion object{
        fun map(dto: AppDataDTO): AppData{
            return AppData(
                appName = dto.appName,
                appGenre = dto.appGenre,
                appDescription = dto.appDescription,
                imagePath = dto.imagePath,
                screenshots = null)
        }

        fun map(compact: AppDataCompact): AppData{
            return AppData(
                appName = compact.name,
                appGenre = compact.category,
                appDescription = compact.description,
                imagePath = compact.iconUrl,
                screenshots = null)
        }

        fun map(full: AppDataFull): AppData{
            return AppData(
                appName = full.name,
                appGenre = full.category,
                appDescription = full.description,
                imagePath = full.iconUrl,
                screenshots = full.screenshotUrlList,
                appAgeRating = full.ageRating,
                appSize = full.size,
                screenshotUrlList = full.screenshotUrlList)
        }
    }
}
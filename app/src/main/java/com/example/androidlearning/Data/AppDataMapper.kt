package com.example.androidlearning.Data

import com.example.androidlearning.Domain.AppData

class AppDataMapper {
    companion object{
        fun map(dto: AppDataDTO): AppData{
            return AppData(
                appName = dto.appName,
                appGenre = dto.appGenre,
                appDescription = dto.appDescription,
                imagePath = dto.imagePath)
        }
    }
}
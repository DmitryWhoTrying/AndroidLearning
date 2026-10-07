package com.example.homework3.ui.theme

data class AppData(
    val appName: String,
    val appGenre: String?,
    val appDescription: String?,
    val imagePath: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppData

        if (appName != other.appName) return false
        if (appGenre != other.appGenre) return false
        if (appDescription != other.appDescription) return false
        if (imagePath != other.imagePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = appName.hashCode()
        result = 31 * result + appGenre.hashCode()
        result = 31 * result + appDescription.hashCode()
        result = 31 * result + imagePath.hashCode()
        return result
    }

}
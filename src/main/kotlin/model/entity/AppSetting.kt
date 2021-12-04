package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class AppSetting(
    val adbLocation: String = "",
    val scrcpyLocation: String = ""
)

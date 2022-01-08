package model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppSetting(
    @SerialName("adbLocation")
    val adbLocation: String = "",
    @SerialName("theme")
    val theme: Theme = Theme.SYNC_WITH_OS,
    @SerialName("scrcpyLocation")
    val scrcpyLocation: String = "",
)

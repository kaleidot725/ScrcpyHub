package model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Setting(
    @SerialName("adbLocation")
    val adbLocation: String = "",
    @SerialName("theme")
    val theme: Theme = Theme.SYNC_WITH_OS,
    @SerialName("scrcpyLocation")
    val scrcpyLocation: String = "",
    @SerialName("screenshotDirectory")
    val screenRecordDirectory: String = "",
    @SerialName("screencaptureDirectory")
    val screenshotDirectory: String = "",
)

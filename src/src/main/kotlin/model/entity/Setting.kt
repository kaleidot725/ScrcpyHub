package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Setting(
    val adbLocation: String? = null,
    val scrcpyLocation: String? = null
)
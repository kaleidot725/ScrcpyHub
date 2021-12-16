package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: String = "",
    val name: String = "",
    var setting: Setting
) {
    val displayName get() = setting.customName ?: name

    @Serializable
    data class Setting(
        val customName: String? = null,
        val maxSize: Int? = null,
        val enableRecording: Boolean = false
    )
}

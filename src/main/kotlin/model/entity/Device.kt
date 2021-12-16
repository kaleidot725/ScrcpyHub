package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: String = "",
    val name: String = ""
) {
    @Serializable
    data class Context(
        val device: Device,
        val customName: String? = null,
        val maxSize: Int? = null,
        val enableRecording: Boolean = false
    ) {
        val displayName get() = customName ?: device.name
    }
}

package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Device(val id: String = "") {
    @Serializable
    data class Context(
        val device: Device,
        val customName: String? = null,
        val maxSize: Int? = null,
        val maxFrameRate: Int? = null,
        val bitrate: Int? = null
    ) {
        val displayName get() = if (customName.isNullOrEmpty()) device.id else customName
    }
}

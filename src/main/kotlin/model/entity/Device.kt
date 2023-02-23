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
        val lockOrientation: Int? = null,
        val bitrate: Int? = null
    ) {
        val displayName get() = if (customName.isNullOrEmpty()) device.id else customName

        enum class LockOrientation(val title: String, val value: Int?) {
            NONE("None", null),
            NATURAL("Natural", 0),
            COUNTER_CLOCK_WISE_90("90° Counter Clock Wise", 1),
            CLOCK_WISE_180("180°", 2),
            CLOCK_WISE_90("90° Clock Wise", 3),
        }
    }
}

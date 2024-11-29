package model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Device(val id: String = "") {
    @Serializable
    data class Context(
        val device: Device,
        val customName: String? = null,
        val enableStayAwake: Boolean = false,
        val enableShowTouches: Boolean = false,
        val enablePowerOffOnClose: Boolean = false,
        val disablePowerOnOnStart: Boolean = false,
        val maxSize: Int? = null,
        val maxFrameRate: Int? = null,
        val lockOrientation: Int? = null,
        val bitrate: Int? = null,
        val buffering: Int? = null,
        val noAudio: Boolean = false,
        val audioBitrate: Int? = null,
        val audioBuffering: Int? = null,
        val enableBorderless: Boolean = false,
        val enableAlwaysOnTop: Boolean = false,
        val enableFullScreen: Boolean = false,
        val enableHidKeyboard: Boolean = false,
        val enableHidMouse: Boolean = false,
        val rotation: Int? = null,
    ) {
        val displayName get() = if (customName.isNullOrEmpty()) device.id else customName

        enum class CaptureOrientation(val value: Int?) {
            NONE(null),
            NATURAL(0),
            COUNTER_CLOCK_WISE_90(270),
            CLOCK_WISE_180(180),
            CLOCK_WISE_90(90),
        }

        enum class Orientation(val value: Int?) {
            NONE(null),
            COUNTER_CLOCK_WISE_90(270),
            CLOCK_WISE_180(180),
            CLOCK_WISE_90(90),
        }
    }
}

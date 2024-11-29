package model.command

import model.entity.Device

class ScrcpyCommandCreator(val scrcpyBinaryPath: String? = null) {
    fun create(context: Device.Context): List<String> {
        return buildList {
            add(resolveScrcpyBinaryPath())
            add(DEVICE_OPTION_NAME)
            add(context.device.id)

            if (context.enableStayAwake) {
                add(STAY_AWAKE_OPTION)
            }

            if (context.enableShowTouches) {
                add(SHOW_TOUCHES_OPTION)
            }

            if (context.enablePowerOffOnClose) {
                add(POWER_OFF_ON_CLOSE)
            }

            if (context.disablePowerOnOnStart) {
                add(DISABLE_POWER_ON_ON_START)
            }

            val maxSize = context.maxSize
            if (maxSize != null) {
                add(MAX_SIZE_OPTION_NAME)
                add(maxSize.toString())
            }

            val maxFrameRate = context.maxFrameRate
            if (maxFrameRate != null) {
                add(MAX_FRAME_RATE_OPTION_NAME)
                add(maxFrameRate.toString())
            }

            val bitrate = context.bitrate
            if (bitrate != null) {
                add(BITRATE_OPTION_NAME)
                add(bitrate.toString() + "M")
            }

            val buffering = context.buffering
            if (buffering != null) {
                add("$DISPLAY_BUFFERING_OPTION_NAME$EQUAL$buffering")
            }

            if (context.noAudio) {
                add(NO_AUDIO_OPTION)
            }

            val audioBitrate = context.audioBitrate
            if (audioBitrate != null) {
                add("$AUDIO_BITRATE_OPTION$EQUAL${audioBitrate}K")
            }

            val audioBuffering = context.audioBuffering
            if (audioBuffering != null) {
                add("$AUDIO_BUFFERING_OPTION$EQUAL$audioBuffering")
            }

            add(WINDOW_TITLE_OPTION_NAME)
            add(context.displayName)

            val lockOrientation = context.lockOrientation
            if (lockOrientation != null) {
                add("$CAPTURE_ORIENTATION_OPTION_NAME$EQUAL$lockOrientation")
            }

            if (context.enableBorderless) {
                add(BORDERLESS_OPTION_NAME)
            }

            if (context.enableAlwaysOnTop) {
                add(ALWAYS_ON_TOP_OPTION_NAME)
            }

            if (context.enableFullScreen) {
                add(FULLSCREEN_OPTION_NAME)
            }

            val rotation = context.rotation
            if (rotation != null) {
                add("$ORIENTATION_OPTION_NAME$EQUAL$rotation")
            }

            if (context.enableHidKeyboard) {
                add(HID_KEYBOARD_OPTION)
            }

            if (context.enableHidMouse) {
                add(HID_MOUSE_OPTION)
            }
        }
    }

    fun createRecord(
        context: Device.Context,
        fileName: String,
    ): List<String> {
        return buildList {
            addAll(create(context))
            add(RECORD_OPTION_NAME)
            add(fileName)
        }
    }

    fun createHelp(): List<String> {
        return buildList {
            add(resolveScrcpyBinaryPath())
            add(HELP_OPTION_NAME)
        }
    }

    private fun resolveScrcpyBinaryPath(): String {
        return scrcpyBinaryPath ?: COMMAND_NAME
    }

    companion object {
        private const val EQUAL = "="
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val MAX_SIZE_OPTION_NAME = "-m"
        private const val HELP_OPTION_NAME = "-h"
        private const val RECORD_OPTION_NAME = "-r"
        private const val MAX_FRAME_RATE_OPTION_NAME = "--max-fps"
        private const val BITRATE_OPTION_NAME = "-b"
        private const val WINDOW_TITLE_OPTION_NAME = "--window-title"
        private const val CAPTURE_ORIENTATION_OPTION_NAME = "--capture-orientation"
        private const val BORDERLESS_OPTION_NAME = "--window-borderless"
        private const val ALWAYS_ON_TOP_OPTION_NAME = "--always-on-top"
        private const val FULLSCREEN_OPTION_NAME = "--fullscreen"
        private const val ORIENTATION_OPTION_NAME = "--orientation"
        private const val DISPLAY_BUFFERING_OPTION_NAME = "--video-buffer"

        private const val NO_AUDIO_OPTION = "--no-audio"
        private const val AUDIO_BITRATE_OPTION = "--audio-bit-rate"
        private const val AUDIO_BUFFERING_OPTION = "--audio-buffer"

        private const val HID_KEYBOARD_OPTION = "--keyboard=uhid"
        private const val HID_MOUSE_OPTION = "--mouse=uhid"

        private const val STAY_AWAKE_OPTION = "--stay-awake"
        private const val SHOW_TOUCHES_OPTION = "--show-touches"
        private const val POWER_OFF_ON_CLOSE = "--power-off-on-close"
        private const val DISABLE_POWER_ON_ON_START = "--no-power-on"
    }
}

package model.command

import model.entity.Device

class ScrcpyCommandCreator(val scrcpyBinaryPath: String? = null) {
    fun create(context: Device.Context): List<String> {
        return buildList {
            add(resolveScrcpyBinaryPath())
            add(DEVICE_OPTION_NAME)
            add(context.device.id)

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

            add(WINDOW_TITLE_OPTION_NAME)
            add(context.displayName)
        }
    }

    fun createRecord(context: Device.Context, fileName: String): List<String> {
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
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val MAX_SIZE_OPTION_NAME = "-m"
        private const val HELP_OPTION_NAME = "-h"
        private const val RECORD_OPTION_NAME = "-r"
        private const val MAX_FRAME_RATE_OPTION_NAME = "--max-fps"
        private const val BITRATE_OPTION_NAME = "-b"
        private const val WINDOW_TITLE_OPTION_NAME = "--window-title"
    }
}

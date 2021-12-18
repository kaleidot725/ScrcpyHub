package model.command.factory

import model.entity.Device

class ScrcpyCommandFactory(val path: String? = null) {
    @OptIn(kotlin.ExperimentalStdlibApi::class)
    fun create(context: Device.Context): List<String> {
        return buildList {
            if (path != null) {
                add("$path$COMMAND_NAME")
            } else {
                add(COMMAND_NAME)
            }

            add(DEVICE_OPTION_NAME)
            add(context.device.id)

            val maxSize = context.maxSize
            if (maxSize != null) {
                add(MAX_SIZE_OPTION_NAME)
                add(maxSize.toString())
            }
        }
    }

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    fun createRecord(context: Device.Context, fileName: String): List<String> {
        return buildList {
            if (path != null) {
                add("$path$COMMAND_NAME")
            } else {
                add(COMMAND_NAME)
            }

            add(DEVICE_OPTION_NAME)
            add(context.device.id)

            val maxSize = context.maxSize
            if (maxSize != null) {
                add(MAX_SIZE_OPTION_NAME)
                add(maxSize.toString())
            }

            add(RECORD_OPTION_NAME)
            add(fileName)
        }
    }

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    fun createHelp(): List<String> {
        return buildList {
            if (path != null) {
                add("$path$COMMAND_NAME")
            } else {
                add(COMMAND_NAME)
            }
            add(HELP_OPTION_NAME)
        }
    }

    companion object {
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val MAX_SIZE_OPTION_NAME = "-m"
        private const val HELP_OPTION_NAME = "-h"
        private const val RECORD_OPTION_NAME = "-r"
    }
}

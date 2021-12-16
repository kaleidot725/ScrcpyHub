package model.command.factory

import model.entity.Device

class ScrcpyCommandFactory(
    override val path: String? = null
) : CommandFactory<Device> {
    @OptIn(kotlin.ExperimentalStdlibApi::class)
    override fun create(data: Device): List<String> {
        return buildList {
            if (path != null) {
                add("$path$COMMAND_NAME")
            } else {
                add(COMMAND_NAME)
            }

            add(DEVICE_OPTION_NAME)
            add(data.id)

            val maxSize = data.setting.maxSize
            if (maxSize != null) {
                add(MAX_SIZE_OPTION_NAME)
                add(maxSize.toString())
            }
        }
    }

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    override fun createHelp(): List<String> {
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

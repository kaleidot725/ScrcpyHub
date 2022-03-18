package model.command.creator

import java.io.File

class AdbCommandCreator(
    val path: String? = null
) {
    fun create(): List<String> {
        return buildList {
            if (path != null) {
                if (path.endsWith(File.separator)) {
                    add("$path$COMMAND_NAME")
                } else {
                    add("$path${File.separator}$COMMAND_NAME")
                }
            } else {
                add(COMMAND_NAME)
            }
        }
    }

    fun createStartSerfver(): List<String> {
        return buildList {
            if (path != null) {
                if (path.endsWith(File.separator)) {
                    add("$path$COMMAND_NAME")
                } else {
                    add("$path${File.separator}$COMMAND_NAME")
                }
            } else {
                add(COMMAND_NAME)
            }
            add(START_SERVER)
        }
    }

    fun createHelp(): List<String> {
        return buildList {
            if (path != null) {
                if (path.endsWith(File.separator)) {
                    add("$path$COMMAND_NAME")
                } else {
                    add("$path${File.separator}$COMMAND_NAME")
                }
            } else {
                add(COMMAND_NAME)
            }
            add(HELP_OPTION)
        }
    }

    companion object {
        private const val COMMAND_NAME = "adb"
        private const val HELP_OPTION = "--help"
        private const val START_SERVER = "start-server"
    }
}

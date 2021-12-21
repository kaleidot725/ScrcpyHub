package model.command.factory

import java.io.File

class AdbCommandFactory(
    val path: String? = null
) {
    @OptIn(kotlin.ExperimentalStdlibApi::class)
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

    @OptIn(kotlin.ExperimentalStdlibApi::class)
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
    }
}

package model.command.factory

import model.command.ScrcpyCommand
import model.entity.Device
import java.io.File

class AdbCommandFactory(
    override val path: String?
): CommandFactory<Unit> {
    override val envPath: String
        get() = path + File.pathSeparator + System.getenv("PATH")

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    override fun create(data: Unit): List<String> {
        return buildList {
            if (path != null) add("$path${COMMAND_NAME}") else add(COMMAND_NAME)
        }
    }

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    override fun createHelp(): List<String> {
        return buildList {
            if (path != null) add("$path${COMMAND_NAME}") else add(COMMAND_NAME)
            add(HELP_OPTION)
        }
    }

    companion object {
        private const val COMMAND_NAME = "adb"
        private const val HELP_OPTION = "--help"
    }
}
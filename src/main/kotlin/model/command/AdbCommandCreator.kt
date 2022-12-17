package model.command

class AdbCommandCreator(
    val adbBinaryPath: String? = null
) {
    fun create(): List<String> {
        return buildList {
            add(resolveAdbBinaryPath())
        }
    }

    fun createStartServer(): List<String> {
        return buildList {
            add(resolveAdbBinaryPath())
            add(START_SERVER)
        }
    }

    fun createHelp(): List<String> {
        return buildList {
            add(resolveAdbBinaryPath())
            add(HELP_OPTION)
        }
    }

    private fun resolveAdbBinaryPath(): String {
        return adbBinaryPath ?: DEFAULT_COMMAND_NAME
    }

    companion object {
        private const val DEFAULT_COMMAND_NAME = "adb"
        private const val HELP_OPTION = "--help"
        private const val START_SERVER = "start-server"
    }
}

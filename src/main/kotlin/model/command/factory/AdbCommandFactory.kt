package model.command.factory

class AdbCommandFactory(
    override val path: String? = null
) : CommandFactory<Unit> {
    @OptIn(kotlin.ExperimentalStdlibApi::class)
    override fun create(data: Unit): List<String> {
        return buildList {
            if (path != null) {
                add("$path$COMMAND_NAME")
            } else {
                add(COMMAND_NAME)
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
            add(HELP_OPTION)
        }
    }

    companion object {
        private const val COMMAND_NAME = "adb"
        private const val HELP_OPTION = "--help"
    }
}

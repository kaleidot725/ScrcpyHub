package model.command

class AdbCommand(private val factory: AdbCommandCreator) {
    fun isInstalled(): Boolean {
        return try {
            ProcessBuilder(factory.createHelp()).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }
}

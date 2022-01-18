package model.command

import model.command.creator.AdbCommandCreator

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

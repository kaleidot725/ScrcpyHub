package model.command

import model.command.factory.AdbCommandFactory

class AdbCommand(private val factory: AdbCommandFactory) {
    fun isInstalled(): Boolean {
        return try {
            ProcessBuilder(factory.createHelp()).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }
}
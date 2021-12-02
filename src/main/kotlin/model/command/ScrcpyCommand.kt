package model.command

import model.command.factory.ScrcpyCommandFactory
import model.entity.Device

class ScrcpyCommand(private val factory: ScrcpyCommandFactory) {
    fun run(device: Device): Process {
        val command = factory.create(device)
        return ProcessBuilder(command).apply { environment()["PATH"] = factory.envPath }.start()
    }

    fun isInstalled(): Boolean {
        return try {
            ProcessBuilder(factory.createHelp()).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }
}

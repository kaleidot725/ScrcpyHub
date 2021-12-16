package model.command

import model.command.factory.ScrcpyCommandFactory
import model.entity.Device
import java.io.File

class ScrcpyCommand(private val factory: ScrcpyCommandFactory) {
    fun run(context: Device.Context): Process {
        val command = factory.create(context)
        return ProcessBuilder(command).apply {
            setupCommandPath(factory.path)
        }.start()
    }

    fun record(context: Device.Context, fileName: String): Process {
        val command = factory.createRecord(context, fileName)
        return ProcessBuilder(command).apply {
            setupCommandPath(factory.path)
        }.start()
    }

    fun isInstalled(): Boolean {
        return try {
            ProcessBuilder(factory.createHelp()).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun ProcessBuilder.setupCommandPath(path: String?) {
        environment()["PATH"] = if (path != null) {
            path + File.pathSeparator + System.getenv("PATH")
        } else {
            System.getenv("PATH")
        }
    }
}

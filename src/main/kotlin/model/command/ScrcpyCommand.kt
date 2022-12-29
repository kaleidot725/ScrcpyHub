package model.command

import model.entity.Device
import java.io.File

class ScrcpyCommand(private val factory: ScrcpyCommandCreator) {
    fun run(context: Device.Context): Process {
        val command = factory.create(context)
        return ProcessBuilder(command).apply {
            setupCommandPath(factory.scrcpyBinaryPath)
        }.start()
    }

    fun record(context: Device.Context, fileName: String): Process {
        val command = factory.createRecord(context, fileName)
        return ProcessBuilder(command).apply {
            setupCommandPath(factory.scrcpyBinaryPath)
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

    private fun ProcessBuilder.setupCommandPath(binaryFile: String?) {
        environment()["PATH"] = if (binaryFile != null) {
            File(binaryFile).parent + File.pathSeparator + System.getenv("PATH")
        } else {
            System.getenv("PATH")
        }
    }
}

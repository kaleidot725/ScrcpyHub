package model.command

import model.entity.Device
import java.io.File

class ScrcpyCommand(
    private var adbPath: String? = null,
    private var scrcpyPath: String? = null
) {
    fun run(device: Device): Process {
        val command = createCommand(scrcpyPath, device)
        return ProcessBuilder(command).apply {
            environment()["PATH"] = adbPath + File.pathSeparator + System.getenv("PATH")
        }.start()
    }

    fun isInstalled(): Boolean {
        return try {
            ProcessBuilder().apply {
                environment()["PATH"] = adbPath + File.pathSeparator + System.getenv("PATH")
            }.command(createHelpCommand(scrcpyPath)).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updatePath(adbPath: String? = null, scrcpyPath: String? = null) {
        this.adbPath = adbPath
        this.scrcpyPath = scrcpyPath
    }

    private fun createCommand(path: String?, device: Device?): List<String> {
        val command = mutableListOf<String>()

        if (path != null) {
            command.add("$path$COMMAND_NAME")
        } else {
            command.add(COMMAND_NAME)
        }

        if (device != null) {
            command.add(DEVICE_OPTION_NAME)
            command.add(device.id)
        }

        return command
    }

    private fun createHelpCommand(path: String?): List<String> {
        val command = mutableListOf<String>()

        if (path != null) {
            command.add("$path$COMMAND_NAME")
        } else {
            command.add(COMMAND_NAME)
        }

        command.add(HELP_OPTION_NAME)
        return command
    }

    companion object {
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val RESOLUTION_OPTION_NAME = "-m"
        private const val HELP_OPTION_NAME = "-h"
    }
}
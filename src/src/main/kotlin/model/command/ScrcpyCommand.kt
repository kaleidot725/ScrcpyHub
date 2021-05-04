package model.command

import model.entity.Device
import model.entity.Resolution
import java.io.File

class ScrcpyCommand(private var adbPath: String? = null, private var scrcpyPath: String? = null) {
    fun run(device: Device? = null, resolution: Resolution? = null, port: Int? = null): Process? {
        return try {
            runCommand()
        } catch (e: SecurityException) {
            null
        } catch (e: NullPointerException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun isInstalled(): Boolean {
        return try {
            runHelpCommand()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updatePath(adbPath: String? = null, scrcpyPath: String? = null) {
        this.adbPath = adbPath
        this.scrcpyPath = scrcpyPath
    }

    private fun runCommand(device: Device? = null, resolution: Resolution? = null, port: Int? = null): Process {
        return if (scrcpyPath != null && scrcpyPath!!.isNotEmpty()) {
            ProcessBuilder().apply {
                environment()["PATH"] = adbPath + File.pathSeparator + System.getenv("PATH")
            }.command(createCommand(scrcpyPath, device, resolution, port)).start()
        } else {
            ProcessBuilder().apply {
                environment()["PATH"] = adbPath + File.pathSeparator + System.getenv("PATH")
            }.command(createCommand(null, device, resolution, port))
                .start()
        }
    }

    private fun runHelpCommand() {
        return if (scrcpyPath != null && scrcpyPath!!.isNotEmpty()) {
            ProcessBuilder()
                .command(createHelpCommand(scrcpyPath))
                .start()
                .destroy()
        } else {
            ProcessBuilder()
                .command(createHelpCommand(null))
                .start()
                .destroy()
        }
    }

    private fun createCommand(path: String?, device: Device?, resolution: Resolution?, port: Int?): List<String> {
        val command = mutableListOf<String>()

        if (path != null) {
            command.add("$path$COMMAND_NAME")
        } else {
            command.add(COMMAND_NAME)
        }

        if (device != null) {
            command.add(" $DEVICE_OPTION_NAME ${device.id}")
        }
        if (resolution != null) {
            command.add(" $RESOLUTION_OPTION_NAME ${resolution.width}")
        }
        if (port != null) {
            command.add((" $PORT_OPTION_NAME $port"))
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
        private const val PORT_OPTION_NAME = "-p"
        private const val HELP_OPTION_NAME = "-h"
    }
}
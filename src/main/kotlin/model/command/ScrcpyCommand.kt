package model.command

import model.entity.Device
import java.io.File

class ScrcpyCommand(
    private var adbPath: String,
    private var scrcpyPath: String
) {
    fun scrcpyIsInstalled(): Boolean {
        return try {
            ProcessBuilder().command(createHelpCommand(scrcpyPath)).start().destroy()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun adbIsInstalled(): Boolean {
        return try {
            ProcessBuilder()
                .command("$adbPath${ADB_COMMAND_NAME}", ADB_DEVICES_OPTION)
                .start()
                .destroy()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun run(device: Device): Process {
        val command = createCommand(scrcpyPath, device)
        return ProcessBuilder(command).apply {
            environment()["PATH"] = adbPath + File.pathSeparator + System.getenv("PATH")
        }.start()
    }

    private fun createCommand(path: String?, device: Device?): List<String> {
        val command = mutableListOf<String>()

        if (path != null) {
            command.add("$path$SCRCPY_COMMAND_NAME")
        } else {
            command.add(SCRCPY_COMMAND_NAME)
        }

        if (device != null) {
            command.add(SCRCPY_DEVICE_OPTION_NAME)
            command.add(device.id)

            val maxSize = device.maxSize
            if (maxSize != null) {
                command.add(SCRCPY_MAX_SIZE_OPTION_NAME)
                command.add(maxSize.toString())
            }
        }
        return command
    }

    private fun createHelpCommand(path: String?): List<String> {
        val command = mutableListOf<String>()

        if (path != null) {
            command.add("$path$SCRCPY_COMMAND_NAME")
        } else {
            command.add(SCRCPY_COMMAND_NAME)
        }

        command.add(SCRCPY_HELP_OPTION_NAME)
        return command
    }

    companion object {
        private const val SCRCPY_COMMAND_NAME = "scrcpy"
        private const val SCRCPY_DEVICE_OPTION_NAME = "-s"
        private const val SCRCPY_MAX_SIZE_OPTION_NAME = "-m"
        private const val SCRCPY_HELP_OPTION_NAME = "-h"

        private const val ADB_COMMAND_NAME = "adb"
        private const val ADB_DEVICES_OPTION = "devices"
    }
}

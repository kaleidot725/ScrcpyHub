package model.command

import model.entity.Device
import model.entity.Resolution
import java.io.File

class ScrcpyCommand(private var path: String? = null) {
    fun run(device: Device? = null, resolution: Resolution? = null, port: Int? = null): Process? {
        return try {
            runRuntimeCommand()
        } catch (e: SecurityException) {
            null
        } catch (e: NullPointerException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun runRuntimeCommand(device: Device? = null, resolution: Resolution? = null, port: Int? = null): Process {
        return if (path != null && path!!.isNotEmpty()) {
            Runtime.getRuntime().exec(createCommand(device, resolution, port), null, File(path))
        } else {
            Runtime.getRuntime().exec(COMMAND_NAME)
        }
    }

    fun isInstalled(): Boolean {
        return try {
            runRuntimeHelpCommand()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updatePath(path: String? = null) {
        this.path = path
    }

    private fun runRuntimeHelpCommand(): Process {
        return if (path != null && path!!.isNotEmpty()) {
            Runtime.getRuntime().exec(createHelpCommand(), null, File(path))
        } else {
            Runtime.getRuntime().exec(createHelpCommand())
        }
    }

    private fun createCommand(device: Device?, resolution: Resolution?, port: Int?): String {
        var command = COMMAND_NAME

        if (device != null) {
            command = command.plus(" $DEVICE_OPTION_NAME ${device.id}")
        }
        if (resolution != null) {
            command = command.plus(" $RESOLUTION_OPTION_NAME ${resolution.width}")
        }
        if (port != null) {
            command = command.plus((" $PORT_OPTION_NAME $port"))
        }

        return command
    }

    private fun createHelpCommand(): String {
        return "$COMMAND_NAME -h"
    }

    companion object {
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val RESOLUTION_OPTION_NAME = "-m"
        private const val PORT_OPTION_NAME = "-p"
        private const val HELP_OPTION_NAME = "-h"
    }
}
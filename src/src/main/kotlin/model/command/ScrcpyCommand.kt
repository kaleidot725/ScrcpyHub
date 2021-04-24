package model.command

import model.entity.Device
import model.entity.Resolution

class ScrcpyCommand() {
    fun run(device: Device? = null, resolution: Resolution? = null, port: Int? = null): Process? {
        return try {
            val command = createCommand(device, resolution, port)
            Runtime.getRuntime().exec(command)
        } catch (e: SecurityException) {
            null
        } catch (e: NullPointerException) {
            null
        } catch (e: IllegalArgumentException) {
            null
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

    companion object {
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val RESOLUTION_OPTION_NAME = "-m"
        private const val PORT_OPTION_NAME = "-p"
    }
}
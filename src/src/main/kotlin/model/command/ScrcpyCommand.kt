package model.command

import model.entity.Device
import model.entity.Resolution

class ScrcpyCommand() {
    fun run(device: Device? = null, resolution: Resolution? = null): Process? {
        return try {
            val command = createCommand(device, resolution)
            Runtime.getRuntime().exec(command)
        } catch (e: SecurityException) {
            null
        } catch (e: NullPointerException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun createCommand(device: Device?, resolution: Resolution?): String {
        return COMMAND_NAME.apply {
            if (device != null) this.plus(" $DEVICE_OPTION_NAME ${device.id}")
            if (resolution != null) this.plus(" $RESOLUTION_OPTION_NAME ${resolution.width}")
        }
    }

    companion object {
        private const val COMMAND_NAME = "scrcpy"
        private const val DEVICE_OPTION_NAME = "-s"
        private const val RESOLUTION_OPTION_NAME = "-m"
    }
}
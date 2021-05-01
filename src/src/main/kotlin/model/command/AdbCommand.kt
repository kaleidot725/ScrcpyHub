package model.command

import com.lordcodes.turtle.shellRun
import model.entity.Device

class AdbCommand {
    fun fetchDevices(): List<Device> {
        return try {
            runCommand().toDeviceList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun isInstalled(): Boolean {
        return try {
            Runtime.getRuntime().exec(COMMAND_NAME)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun runCommand(): String {
        return shellRun(COMMAND_NAME, listOf(DEVICES_OPTION))
    }

    private fun String.toDeviceList(): List<Device> {
        // get device list
        val devices = this.split("\n").toMutableList()

        // remove device list header
        devices.removeAt(0)

        // convert string to device
        return devices.map { str ->
            val splits = str.split("\t")
            Device(splits[0], splits[1])
        }
    }

    companion object {
        private const val COMMAND_NAME = "adb"
        private const val DEVICES_OPTION = "devices"
    }
}
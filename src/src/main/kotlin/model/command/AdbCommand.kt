package model.command

import com.lordcodes.turtle.shellRun
import model.entity.Device
import java.io.File

class AdbCommand(private var path: String? = null) {
    fun fetchDevices(): List<Device> {
        return try {
            runCommand().toDeviceList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun isInstalled(): Boolean {
        return try {
            runRuntimeCommand()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updatePath(path: String? = null) {
        this.path = path
    }

    private fun runCommand(): String {
        return if (path != null && path!!.isNotEmpty()) {
            shellRun(COMMAND_NAME, listOf(DEVICES_OPTION), File(path ?: ""))
        } else {
            shellRun(COMMAND_NAME, listOf(DEVICES_OPTION))
        }
    }

    private fun runRuntimeCommand() {
        if (path != null && path!!.isNotEmpty()) {
            Runtime.getRuntime().exec(COMMAND_NAME, null, File(path))
        } else {
            Runtime.getRuntime().exec(COMMAND_NAME)
        }
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
package model.command

import com.lordcodes.turtle.shellRun
import model.entity.Device

class AdbCommand {
    fun getDevices(): List<Device> {
        try {
            // get device list
            val output = shellRun("adb", listOf("devices"))
            val devices = output.split("\n").toMutableList()

            // remove device list header
            devices.removeAt(0)

            // convert string to device
            return devices.map { str ->
                val splits = str.split("\t")
                Device(splits[0], splits[1])
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}
package model.repository

import command.AdbCommand
import command.ScrcpyCommand
import model.Device

class DeviceRepository(
    private val adbCommand: AdbCommand,
    private val scrcpyCommand: ScrcpyCommand
) {
    private var selected: Device? = null
    private var process: Process? = null

    fun getAll(): List<Device> {
        return adbCommand.getDevices()
    }

    fun select(device: Device) {
        selected = device
    }

    fun run(): Boolean {
        if (selected == null) {
            return false
        }

        try {
            process?.destroy()
            process = scrcpyCommand.run(selected!!)
            return true
        } catch (e: Exception) {
            process = null
            return false
        }
    }

    fun stop(): Boolean {
        process?.destroy()
        process = null
        return true
    }
}
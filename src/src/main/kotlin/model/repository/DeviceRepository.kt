package model.repository

import command.AdbCommand
import model.Device

class DeviceRepository(private val adbCommand: AdbCommand) {
    fun getAll(): List<Device> {
        return adbCommand.getDevices()
    }
}
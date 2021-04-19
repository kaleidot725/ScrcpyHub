package model.repository

import model.command.AdbCommand
import model.entity.Device

class DeviceRepository(
    private val adbCommand: AdbCommand
) {
    var selected: Device? = getAll().firstOrNull()

    fun getAll(): List<Device> {
        return adbCommand.fetchDevices()
    }
}
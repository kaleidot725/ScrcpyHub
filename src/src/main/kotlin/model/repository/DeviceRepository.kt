package model.repository

import model.Device

class DeviceRepository {
    fun getAll(): List<Device> = DEVICE_LIST

    companion object {
        val DEVICE_LIST = listOf(
            Device("one", "device-one"),
            Device("two", "device-two"),
        )
    }
}
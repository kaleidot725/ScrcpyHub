package model.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.command.AdbCommand
import model.entity.Device
import model.utils.FileUtils

class DeviceRepository(
    private val root: String,
    private val adbCommand: AdbCommand
) {
    fun getAll(): List<Device> {
        return adbCommand.fetchDevices().map { loadCache(it) }
    }

    fun rename(device: Device, newName: String) {
        createDir()
        writeCache(device.copy(customName = newName))
    }

    private fun writeCache(device: Device) {
        try {
            FileUtils.createFileFile(root, device.id).outputStream().apply {
                this.write(Json.encodeToString(device).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun loadCache(device: Device): Device {
        return try {
            val content = FileUtils.createFileFile(root, device.id).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            device
        }
    }

    private fun createDir() {
        try {
            val file = FileUtils.createDirFile(root)
            if (!file.exists()) file.mkdir()
        } catch (e: Exception) {
            return
        }
    }
}
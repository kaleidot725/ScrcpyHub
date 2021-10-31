package model.repository

import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.request.device.ListDevicesRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.Device
import model.utils.FileUtils

class DeviceRepository(private val root: String) {
    private val adb = AndroidDebugBridgeClientFactory().build()
    
    suspend fun getAll(): List<Device> {
        val devices: List<Device> = adb.execute(request = ListDevicesRequest()).map { it.toDevice() }
        return devices.map { loadCache(it) }
    }

    fun updateSetting(device: Device, newName: String, newMaxSize: Int?) {
        createDir()
        writeCache(device.copy(customName = newName, maxSize = newMaxSize))
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

    private fun com.malinskiy.adam.request.device.Device.toDevice(): Device {
        return Device(id = serial, name = "Device")
    }
}
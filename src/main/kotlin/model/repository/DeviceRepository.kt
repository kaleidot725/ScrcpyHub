package model.repository

import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.framebuffer.RawImageScreenCaptureAdapter
import com.malinskiy.adam.request.framebuffer.ScreenCaptureRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.Device
import model.os.OSContext
import java.io.File
import javax.imageio.ImageIO

class DeviceRepository(private val osContext: OSContext) {
    private val adb = AndroidDebugBridgeClientFactory().build()
    private val screenshotAdapter = RawImageScreenCaptureAdapter()

    suspend fun getAll(): List<Device.Context> {
        return withContext(Dispatchers.IO) {
            val devices: List<Device> = adb.execute(request = ListDevicesRequest()).toDeviceList()
            loadCaches(devices)
        }
    }

    suspend fun saveDeviceSetting(context: Device.Context) {
        withContext(Dispatchers.IO) {
            createDir()
            writeCache(context)
        }
    }

    suspend fun saveScreenshot(device: Device, filePath: String): Boolean {
        return withContext(Dispatchers.IO) {
            val image = adb.execute(
                request = ScreenCaptureRequest(screenshotAdapter),
                serial = device.id
            ).toBufferedImage()
            ImageIO.write(image, "png", File(filePath))
        }
    }

    private fun writeCache(deviceContext: Device.Context) {
        try {
            File(osContext.settingPath + deviceContext.device.id).outputStream().apply {
                this.write(Json.encodeToString(deviceContext).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun loadCaches(devices: List<Device>): List<Device.Context> {
        return devices.map { loadCache(it) }
    }

    private fun loadCache(device: Device): Device.Context {
        return try {
            val content = File(osContext.settingPath + device.id).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            Device.Context(device = device)
        }
    }

    private fun createDir() {
        try {
            val file = File(osContext.settingPath)
            if (!file.exists()) file.mkdir()
        } catch (e: Exception) {
            return
        }
    }

    private fun List<com.malinskiy.adam.request.device.Device>.toDeviceList(): List<Device> {
        return this.map { it.toDevice() }
    }

    private fun com.malinskiy.adam.request.device.Device.toDevice(): Device {
        return Device(id = serial)
    }
}

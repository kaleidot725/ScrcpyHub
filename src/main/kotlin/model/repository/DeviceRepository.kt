package model.repository

import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.request.device.AsyncDeviceMonitorRequest
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.framebuffer.RawImageScreenCaptureAdapter
import com.malinskiy.adam.request.framebuffer.ScreenCaptureRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.Device
import model.utils.FileUtils
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

class DeviceRepository(private val root: String) {
    private val adb = AndroidDebugBridgeClientFactory().build()
    private val screenshotAdapter = RawImageScreenCaptureAdapter()

    suspend fun getAll(): List<Device> {
        return withContext(Dispatchers.IO) {
            val devices: List<Device> = adb.execute(request = ListDevicesRequest()).toDeviceList()
            loadCaches(devices)
        }
    }

    fun getAllFlow(scope: CoroutineScope): Flow<List<Device>> {
        val allFlow = adb.execute(request = AsyncDeviceMonitorRequest(), scope = scope).receiveAsFlow()
        return allFlow.map { loadCaches(it.toDeviceList()) }
    }

    suspend fun saveDeviceSetting(device: Device, newName: String, newMaxSize: Int?) {
        withContext(Dispatchers.IO) {
            createDir()
            writeCache(device.copy(customName = newName, maxSize = newMaxSize))
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

    fun createScreenshotPathForDesktop(device: Device): String {
        val date = ZonedDateTime
            .now(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
        return "${System.getProperty("user.home")}/Desktop/${device.displayName}-${date}.png"
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

    private fun loadCaches(devices: List<Device>): List<Device> {
        return devices.map { loadCache(it) }
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

    private fun List<com.malinskiy.adam.request.device.Device>.toDeviceList(): List<Device> {
        return this.map { it.toDevice() }
    }

    private fun com.malinskiy.adam.request.device.Device.toDevice(): Device {
        return Device(id = serial, name = "Device")
    }
}

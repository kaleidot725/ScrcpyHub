package model.repository

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.entity.Device
import model.entity.Resolution
import model.extension.monitor
import model.extension.waitForRunning

class DeviceRepository(
    private val adbCommand: AdbCommand,
    private val scrcpyCommand: ScrcpyCommand
) {
    private var process: Process? = null
    var selected: Device? = null

    fun getAll(): List<Device> {
        return adbCommand.getDevices()
    }

    fun run(
        device: Device? = null,
        resolution: Resolution? = null,
        onDestroy: (suspend () -> Unit)? = null
    ): Boolean {
        return try {
            process?.destroy()
            process = scrcpyCommand.run(device, resolution)
            MainScope().launch {
                process?.waitForRunning(1000)
                process?.monitor(1000) { onDestroy?.invoke() }
            }
            true
        } catch (e: Exception) {
            process = null
            false
        }
    }

    fun stop() {
        process?.destroy()
        process = null
    }
}
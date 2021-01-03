package model.repository

import command.AdbCommand
import command.ScrcpyCommand
import extension.monitor
import extension.waitForRunning
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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

    fun run(onDestroy: suspend () -> Unit): Boolean {
        if (selected == null) {
            return false
        }

        return try {
            process?.destroy()
            process = scrcpyCommand.run(selected!!)
            MainScope().launch {
                process?.waitForRunning(1000)
                process?.monitor(1000) { onDestroy.invoke() }
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
package model.repository

import command.AdbCommand
import command.ScrcpyCommand
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
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

        try {
            process?.destroy()
            process = scrcpyCommand.run(selected!!)
            waitFor(process!!, onDestroy)
            return true
        } catch (e: Exception) {
            process = null
            return false
        }
    }

    fun stop() {
        process?.destroy()
        process = null
    }

    private fun waitFor(process: Process, onDestroy: suspend () -> Unit) {
        MainScope().launch {
            while (this.isActive) {
                delay(1000)
                if (!process.isAlive) {
                    onDestroy.invoke()
                    break
                }
            }
        }
    }
}
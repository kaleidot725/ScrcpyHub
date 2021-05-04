package model.command

import model.entity.Device
import java.io.BufferedReader
import java.io.InputStreamReader

class AdbCommand(private var adbPath: String? = null) {
    fun fetchDevices(): List<Device> {
        return try {
            runCommand().toDeviceList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun isInstalled(): Boolean {
        return try {
            runRuntimeCommand()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updatePath(adbPath: String? = null) {
        this.adbPath = adbPath
    }

    private fun runCommand(): List<String> {
        return if (adbPath != null && adbPath!!.isNotEmpty()) {
            val process = ProcessBuilder()
                .command("$adbPath$COMMAND_NAME", DEVICES_OPTION)
                .start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            reader.readAllLine().apply {
                reader.close()
                process.destroy()
            }
        } else {
            val process = ProcessBuilder()
                .command(COMMAND_NAME, DEVICES_OPTION)
                .start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            reader.readAllLine().apply {
                reader.close()
                process.destroy()
            }
        }
    }

    private fun runRuntimeCommand() {
        if (adbPath != null && adbPath!!.isNotEmpty()) {
            ProcessBuilder()
                .command("$adbPath$COMMAND_NAME", DEVICES_OPTION)
                .start()
                .destroy()
        } else {
            ProcessBuilder()
                .command(COMMAND_NAME)
                .start()
                .destroy()
        }
    }

    private fun BufferedReader.readAllLine(): List<String> {
        val output = mutableListOf<String>()
        var line: String? = null
        while (true) {
            line = this.readLine()
            if (line == null) {
                break
            }

            output.add(line)
        }

        return output
    }

    private fun List<String>.toDeviceList(): List<Device> {
        // get device list
        val devices = this.filter { it.isNotBlank() && it.isNotEmpty() }.toMutableList()

        // remove device list header
        devices.removeAt(0)

        // convert string to device
        return devices.map { str ->
            val splits = str.split("\t")
            Device(splits[0], splits[1])
        }
    }

    companion object {
        private const val COMMAND_NAME = "adb"
        private const val DEVICES_OPTION = "devices"
    }
}
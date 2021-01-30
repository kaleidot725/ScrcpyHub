package model.command

import model.entity.Device
import model.entity.Resolution

class ScrcpyCommand() {
    fun run(device: Device? = null, resolution: Resolution? = null): Process? {
        var command = "scrcpy"

        if (device != null) {
            command = command.plus(" -s ${device.id}")
        }

        if (resolution != null) {
            command = command.plus(" -m ${resolution.width}")
        }

        try {
            return Runtime.getRuntime().exec(command)
        } catch (e: SecurityException) {
            return null
        } catch (e: NullPointerException) {
            return null
        } catch (e: IllegalArgumentException) {
            return null
        }
    }
}
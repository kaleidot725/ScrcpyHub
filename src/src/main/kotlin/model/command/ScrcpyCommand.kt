package model.command

import model.entity.Device
import model.entity.Resolution

class ScrcpyCommand {
    fun run(device: Device? = null, resolution: Resolution? = null): Process {
        var command = "scrcpy"

        if (device != null) {
            command = command.plus(" -s ${device.id}")
        }

        if (resolution != null) {
            command = command.plus(" -m ${resolution.width}")
        }

        println(command)
        return Runtime.getRuntime().exec(command)
    }
}
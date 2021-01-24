package command

import model.Device
import model.Resolution

class ScrcpyCommand {
    fun run(device: Device? = null, resolution: Resolution? = null): Process {
        var command = "scrcpy"

        if (device != null) {
            command = command.plus(" -s ${device.id}")
        }

        if (resolution != null) {
            command = command.plus("-m ${resolution.width}")
        }

        return Runtime.getRuntime().exec(command)
    }
}
package command

import model.Device

class ScrcpyCommand {
    fun run(device: Device): Process {
        return Runtime.getRuntime().exec("scrcpy -s ${device.id}")
    }
}
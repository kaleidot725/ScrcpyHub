package model.usecase

import model.command.ScrcpyCommand
import model.entity.Device
import model.entity.Resolution
import model.repository.ProcessRepository

class StartScrcpyUseCase(
    private val scrcpyCommand: ScrcpyCommand,
    private val processRepository: ProcessRepository
) {
    fun execute(device: Device? = null, resolution: Resolution? = null): Boolean {
        val process = scrcpyCommand.run(device, resolution)
        

        return (process != null)
    }
}
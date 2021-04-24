package model.usecase

import model.command.ScrcpyCommand
import model.entity.Device
import model.entity.Resolution
import model.repository.PortRepository
import model.repository.ProcessRepository

class StartScrcpyUseCase(
    private val scrcpyCommand: ScrcpyCommand,
    private val processRepository: ProcessRepository,
    private val portRepository: PortRepository
) {
    suspend fun execute(device: Device, resolution: Resolution?, onDestroy: suspend () -> Unit): Boolean {
        val exists = processRepository.any(device.id)
        if (exists) {
            return false
        }

        val port = portRepository.allocate(device.id) ?: return false
        val process = scrcpyCommand.run(device, resolution, port) ?: return false

        processRepository.insert(device.id, process, onDestroy)
        return true
    }
}
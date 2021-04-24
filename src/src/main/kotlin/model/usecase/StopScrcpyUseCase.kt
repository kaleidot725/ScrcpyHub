package model.usecase

import model.entity.Device
import model.repository.PortRepository
import model.repository.ProcessRepository

class StopScrcpyUseCase(
    private val processRepository: ProcessRepository,
    private val portRepository: PortRepository
) {
    suspend fun execute(device: Device): Boolean {
        portRepository.release(device.id)
        processRepository.delete(device.id)
        return true
    }
}
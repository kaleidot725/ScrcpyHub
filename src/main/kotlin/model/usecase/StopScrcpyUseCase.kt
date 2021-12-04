package model.usecase

import model.entity.Device
import model.repository.ProcessRepository

class StopScrcpyUseCase(
    private val processRepository: ProcessRepository
) {
    fun execute(device: Device): Boolean {
        processRepository.delete(device.id)
        return true
    }
}

package model.usecase

import model.entity.Device
import model.repository.ProcessRepository

class IsRunningScrcpyUseCase(
    private val processRepository: ProcessRepository
) {
    fun execute(device: Device): Boolean {
        return processRepository.any(device.id)
    }
}
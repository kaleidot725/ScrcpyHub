package model.usecase

import model.entity.Device
import model.repository.ProcessRepository

class StopScrcpyUseCase(
    private val processRepository: ProcessRepository
) {
    fun execute(context: Device.Context): Boolean {
        processRepository.delete(context.device.id)
        return true
    }
}

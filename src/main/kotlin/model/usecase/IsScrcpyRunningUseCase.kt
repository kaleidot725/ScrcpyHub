package model.usecase

import model.entity.Device
import model.repository.ProcessRepository

class IsScrcpyRunningUseCase(
    private val processRepository: ProcessRepository
) {
    fun execute(context: Device.Context): Boolean {
        return processRepository.any(context.device.id)
    }
}

package model.usecase

import model.entity.Device
import model.repository.ProcessRepository
import model.repository.ProcessStatus

class GetScrcpyStatusUseCase(
    private val processRepository: ProcessRepository
) {
    fun execute(context: Device.Context): ProcessStatus {
        return processRepository.getStatus(context.device.id)
    }
}

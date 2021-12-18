package model.usecase

import model.entity.Device
import model.repository.ProcessRepository
import model.repository.ProcessStatus
import model.repository.SettingRepository

class StartScrcpyUseCase(
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val lastState = processRepository.getStatus(context.device.id)
        if (lastState != ProcessStatus.IDLE) {
            return false
        }

        return try {
            processRepository.addMirroringProcess(context, settingRepository.get().scrcpyLocation) {
                onDestroy.invoke()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

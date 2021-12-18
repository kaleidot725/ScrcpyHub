package model.usecase

import model.entity.Device
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ProcessStatus
import model.repository.SettingRepository

class StartScrcpyRecordUseCase(
    private val deviceRepository: DeviceRepository,
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val lastState = processRepository.getStatus(context.device.id)
        if (lastState != ProcessStatus.IDLE) {
            return false
        }

        return try {
            val fileName = deviceRepository.createRecordPathForDesktop(context)
            val scrcpyLocation = settingRepository.get().scrcpyLocation
            processRepository.addRecordingProcess(context, fileName, scrcpyLocation) {
                onDestroy.invoke()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

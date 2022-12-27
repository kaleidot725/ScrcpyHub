package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.MessageRepository
import model.repository.ProcessRepository
import model.repository.ProcessStatus
import model.repository.SettingRepository

class StartScrcpyUseCase(
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository,
    private val messageRepository: MessageRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val lastState = processRepository.getStatus(context.device.id)
        if (lastState != ProcessStatus.Idle) {
            return false
        }

        return try {
            processRepository.addMirroringProcess(context, settingRepository.get().scrcpyLocation) {
                onDestroy.invoke()
            }
            messageRepository.notify(Message.Notify.StartMirroring(context))
            true
        } catch (e: Exception) {
            messageRepository.notify(Message.Notify.FailedMirroring(context))
            false
        }
    }
}

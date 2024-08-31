package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.MessageRepository
import model.repository.ProcessRepository

class StopScrcpyRecordUseCase(
    private val processRepository: ProcessRepository,
    private val messageRepository: MessageRepository,
) {
    suspend fun execute(context: Device.Context): Boolean {
        processRepository.delete(context.device.id)
        messageRepository.notify(Message.Notify.StopRecordingMovie(context))
        return true
    }
}

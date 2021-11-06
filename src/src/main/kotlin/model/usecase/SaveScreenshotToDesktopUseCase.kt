package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.DeviceRepository
import model.repository.MessageRepository

class SaveScreenshotToDesktopUseCase(
    private val deviceRepository: DeviceRepository,
    private val messageRepository: MessageRepository,
) {
    suspend fun execute(device: Device): Boolean {
        return deviceRepository.saveScreenshot(device).apply {
            val message = if (this) Message.SaveScreenshotSuccessMessage else Message.SaveScreenshotFailedMessage
            messageRepository.push(message)
        }
    }
}
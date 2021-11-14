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
        val filePath = deviceRepository.createScreenshotPathForDesktop(device)
        return deviceRepository.saveScreenshot(device, filePath).apply {
            val message = if (this) {
                Message.SuccessToSaveScreenshot(device, filePath)
            } else {
                Message.FailedToSaveScreenshot(device)
            }
            messageRepository.push(message)
        }
    }
}
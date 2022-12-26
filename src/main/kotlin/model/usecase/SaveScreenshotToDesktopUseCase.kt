package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.DeviceRepository
import model.repository.MessageRepository

class SaveScreenshotToDesktopUseCase(
    private val deviceRepository: DeviceRepository,
    private val messageRepository: MessageRepository,
) {
    suspend fun execute(context: Device.Context): Boolean {
        val filePath = deviceRepository.createScreenshotPathForDesktop(context)
        return deviceRepository.saveScreenshot(context.device, filePath).apply {
            val message = if (this) {
                Message.Notify.SuccessToSaveScreenshot(context, filePath)
            } else {
                Message.Notify.FailedToSaveScreenshot(context)
            }
            messageRepository.notify(message)
        }
    }
}

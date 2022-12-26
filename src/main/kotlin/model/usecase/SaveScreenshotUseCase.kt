package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.DeviceRepository
import model.repository.MessageRepository
import model.repository.SettingRepository
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SaveScreenshotUseCase(
    private val deviceRepository: DeviceRepository,
    private val messageRepository: MessageRepository,
    private val settingRepository: SettingRepository
) {
    suspend fun execute(context: Device.Context): Boolean {
        val directory = createScreenshotDirectory()
        if (!File(directory).exists()) {
            messageRepository.notify(Message.Notify.FailedToSaveScreenshot(context))
            return false
        }

        val filePath = createScreenshotPath(directory, context)
        return deviceRepository.saveScreenshot(context.device, filePath).apply {
            val message = if (this) {
                Message.Notify.SuccessToSaveScreenshot(context, filePath)
            } else {
                Message.Notify.FailedToSaveScreenshot(context)
            }
            messageRepository.notify(message)
        }
    }

    private suspend fun createScreenshotDirectory(): String {
        val screenshotDirectory = settingRepository.get().screenshotDirectory
        return if (screenshotDirectory.isEmpty()) {
            "${System.getProperty("user.home")}/Desktop/"
        } else {
            if (screenshotDirectory.endsWith("/")) {
                screenshotDirectory
            } else {
                "${screenshotDirectory}/"
            }
        }
    }

    private fun createScreenshotPath(directory: String, context: Device.Context): String {
        val date = ZonedDateTime
            .now(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
        return "$directory${context.displayName}-$date.png"
    }
}

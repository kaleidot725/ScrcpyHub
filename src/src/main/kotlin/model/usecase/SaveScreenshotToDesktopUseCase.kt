package model.usecase

import model.entity.Device
import model.repository.DeviceRepository
import model.repository.MessageRepository

class SaveScreenshotToDesktop(
    private val deviceRepository: DeviceRepository,
    private val messageEventRepository: MessageRepository,
) {
    suspend fun execute(device: Device): Boolean {
        val result = deviceRepository.saveScreenshot(device)
        if (result) {

        }
        return
    }
}
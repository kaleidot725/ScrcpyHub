package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class SaveScreenshotToDesktop(private val deviceRepository: DeviceRepository) {
    suspend fun execute(device: Device): Boolean {
        return deviceRepository.saveScreenshot(device)
    }
}
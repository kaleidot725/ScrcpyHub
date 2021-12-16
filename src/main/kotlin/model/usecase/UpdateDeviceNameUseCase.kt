package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class UpdateDeviceNameUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun execute(newContext: Device.Context): Boolean {
        return try {
            deviceRepository.saveDeviceSetting(newContext)
            true
        } catch (e: Exception) {
            false
        }
    }
}

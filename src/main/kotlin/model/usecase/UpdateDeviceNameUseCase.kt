package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class UpdateDeviceNameUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun execute(device: Device, newSetting: Device.Setting): Boolean {
        return try {
            deviceRepository.saveDeviceSetting(device, newSetting)
            true
        } catch (e: Exception) {
            false
        }
    }
}

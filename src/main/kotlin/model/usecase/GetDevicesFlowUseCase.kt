package model.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import model.entity.Device
import model.repository.DeviceRepository

class GetDevicesFlowUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun get(scope: CoroutineScope): Flow<List<Device.Context>> {
        deviceRepository.getAll()
        return deviceRepository.getAllFlow(scope)
    }
}

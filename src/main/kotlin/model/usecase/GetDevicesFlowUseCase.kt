package model.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import model.entity.Device
import model.repository.DeviceRepository

class GetDevicesFlowUseCase(private val deviceRepository: DeviceRepository) {
    fun get(scope: CoroutineScope): Flow<List<Device.Context>> {
        return deviceRepository.getAllFlow(scope)
    }
}

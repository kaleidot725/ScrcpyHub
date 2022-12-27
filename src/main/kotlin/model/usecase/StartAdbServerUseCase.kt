package model.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.repository.SettingRepository
import model.service.AdbServerService

class StartAdbServerUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            val setting = settingRepository.get()
            return@withContext AdbServerService.startAdbServer(setting.adbLocation)
        }
    }
}

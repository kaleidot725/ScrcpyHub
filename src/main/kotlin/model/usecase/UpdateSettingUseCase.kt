package model.usecase

import model.entity.Setting
import model.repository.SettingRepository

class UpdateSettingUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend fun execute(setting: Setting) {
        return settingRepository.update(setting)
    }
}

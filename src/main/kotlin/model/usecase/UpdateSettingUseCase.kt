package model.usecase

import model.entity.AppSetting
import model.repository.SettingRepository

class UpdateSettingUseCase(
    private val settingRepository: SettingRepository
) {
    suspend fun execute(setting: AppSetting) {
        return settingRepository.update(setting)
    }
}
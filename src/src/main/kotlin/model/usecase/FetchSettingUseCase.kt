package model.usecase

import model.entity.AppSetting
import model.repository.SettingRepository

class FetchSettingUseCase(private val settingRepository: SettingRepository) {
    fun execute(): AppSetting {
        return settingRepository.get()
    }
}
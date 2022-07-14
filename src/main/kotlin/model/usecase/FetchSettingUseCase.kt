package model.usecase

import model.entity.Setting
import model.repository.SettingRepository

class FetchSettingUseCase(private val settingRepository: SettingRepository) {
    suspend fun execute(): Setting {
        return settingRepository.get()
    }
}

package model.usecase

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.entity.Setting
import model.repository.SettingRepository

class UpdateSettingUseCase(
    private val adbCommand: AdbCommand,
    private val scrcpyCommand: ScrcpyCommand,
    private val settingRepository: SettingRepository
) {
    fun execute(setting: Setting) {
        adbCommand.setupPath(setting.adbLocation ?: "")
        scrcpyCommand.setupPath(setting.adbLocation ?: "", setting.scrcpyLocation ?: "")
        return settingRepository.update(setting)
    }
}
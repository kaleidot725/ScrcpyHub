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
        adbCommand.updatePath(setting.adbLocation)
        scrcpyCommand.updatePath(setting.scrcpyLocation)
        return settingRepository.update(setting)
    }
}
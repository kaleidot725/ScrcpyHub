package model.usecase

import model.command.AdbCommand
import model.command.creator.AdbCommandCreator
import model.repository.SettingRepository

class StartAdbServerUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Boolean {
        val setting = settingRepository.get()
        val adbCommand = AdbCommand(AdbCommandCreator(setting.adbLocation))
        return adbCommand.startAdbServer()
    }
}

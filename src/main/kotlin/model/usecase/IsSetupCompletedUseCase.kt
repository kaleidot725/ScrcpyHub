package model.usecase

import model.command.ScrcpyCommand
import model.repository.SettingRepository

class IsSetupCompletedUseCase(
    private val settingRepository: SettingRepository
) {
    suspend fun execute(): Result {
        val setting = settingRepository.get()
        val scrcpyCommand = ScrcpyCommand(setting.adbLocation, setting.scrcpyLocation)
        
        if (!scrcpyCommand.adbIsInstalled()) {
            return Result.NOT_FOUND_ADB_COMMAND
        }

        if (!scrcpyCommand.scrcpyIsInstalled()) {
            return Result.NOT_FOUND_SCRCPY_COMMAND
        }

        return Result.OK
    }

    enum class Result {
        OK,
        NOT_FOUND_ADB_COMMAND,
        NOT_FOUND_SCRCPY_COMMAND
    }
}
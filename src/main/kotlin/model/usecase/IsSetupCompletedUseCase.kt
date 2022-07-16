package model.usecase

import model.command.AdbCommand
import model.command.AdbCommandCreator
import model.command.ScrcpyCommand
import model.command.ScrcpyCommandCreator
import model.repository.SettingRepository

class IsSetupCompletedUseCase(
    private val settingRepository: SettingRepository
) {
    suspend fun execute(): Result {
        val setting = settingRepository.get()

        val adbCommand = AdbCommand(AdbCommandCreator(setting.adbLocation))
        if (!adbCommand.isInstalled()) {
            return Result.NOT_FOUND_ADB_COMMAND
        }

        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandCreator(setting.scrcpyLocation))
        if (!scrcpyCommand.isInstalled()) {
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

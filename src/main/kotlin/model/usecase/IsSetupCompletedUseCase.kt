package model.usecase

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.command.factory.AdbCommandFactory
import model.command.factory.ScrcpyCommandFactory
import model.repository.SettingRepository

class IsSetupCompletedUseCase(
    private val settingRepository: SettingRepository
) {
    suspend fun execute(): Result {
        val setting = settingRepository.get()

        val adbCommand = AdbCommand(AdbCommandFactory(setting.adbLocation))
        if (!adbCommand.isInstalled()) {
            return Result.NOT_FOUND_ADB_COMMAND
        }

        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandFactory(setting.scrcpyLocation))
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
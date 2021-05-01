package model.usecase

import model.command.AdbCommand
import model.command.ScrcpyCommand

class IsSetupCompletedUseCase(
    private val adbCommand: AdbCommand,
    private val scrcpyCommand: ScrcpyCommand
) {
    fun execute(): Result {
        if (!adbCommand.isInstalled()) {
            return Result.NOT_FOUND_ADB_COMMAND
        }

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
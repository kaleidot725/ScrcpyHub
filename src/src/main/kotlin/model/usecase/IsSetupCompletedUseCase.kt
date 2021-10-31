package model.usecase

import model.command.ScrcpyCommand

class IsSetupCompletedUseCase(private val scrcpyCommand: ScrcpyCommand) {
    fun execute(): Result {
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
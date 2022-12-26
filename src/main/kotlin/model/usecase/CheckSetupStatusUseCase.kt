package model.usecase

import model.command.AdbCommand
import model.command.AdbCommandCreator
import model.command.ScrcpyCommand
import model.command.ScrcpyCommandCreator
import model.entity.Message
import model.repository.MessageRepository
import model.repository.SettingRepository

class CheckSetupStatusUseCase(
    private val messageRepository: MessageRepository,
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke() {
        val setting = settingRepository.get()

        val adbCommand = AdbCommand(AdbCommandCreator(setting.adbLocation))
        if (!adbCommand.isInstalled()) {
            messageRepository.pushError(Message.Error.NotFoundAdbBinary)
        } else {
            messageRepository.popError(Message.Error.NotFoundAdbBinary)
        }

        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandCreator(setting.scrcpyLocation))
        if (!scrcpyCommand.isInstalled()) {
            messageRepository.pushError(Message.Error.NotFoundScrcpyBinary)
        } else {
            messageRepository.popError(Message.Error.NotFoundScrcpyBinary)
        }
    }
}

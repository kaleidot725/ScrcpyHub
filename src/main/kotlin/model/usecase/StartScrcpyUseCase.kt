package model.usecase

import model.command.ScrcpyCommand
import model.command.factory.ScrcpyCommandFactory
import model.entity.Device
import model.repository.ProcessRepository
import model.repository.SettingRepository

class StartScrcpyUseCase(
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val exists = processRepository.any(context.device.id)
        if (exists) {
            return false
        }

        val setting = settingRepository.get()
        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandFactory(setting.scrcpyLocation))

        return try {
            val process = scrcpyCommand.run(context)
            processRepository.insert(context.device.id, process) {
                processRepository.delete(context.device.id)
                onDestroy.invoke()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

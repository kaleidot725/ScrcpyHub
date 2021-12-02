package model.usecase

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.command.factory.AdbCommandFactory
import model.command.factory.ScrcpyCommandFactory
import model.entity.Device
import model.repository.ProcessRepository
import model.repository.SettingRepository

class StartScrcpyUseCase(
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository
) {
    suspend fun execute(device: Device, onDestroy: suspend () -> Unit): Boolean {
        val exists = processRepository.any(device.id)
        if (exists) {
            return false
        }

        val setting = settingRepository.get()
        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandFactory(setting.scrcpyLocation))

        return try {
            val process = scrcpyCommand.run(device)
            processRepository.insert(device.id, process) {
                processRepository.delete(device.id)
                onDestroy.invoke()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
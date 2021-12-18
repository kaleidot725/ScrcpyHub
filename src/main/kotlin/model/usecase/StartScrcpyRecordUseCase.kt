package model.usecase

import model.command.ScrcpyCommand
import model.command.factory.ScrcpyCommandFactory
import model.entity.Device
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ProcessStatus
import model.repository.SettingRepository

class StartScrcpyRecordUseCase(
    private val deviceRepository: DeviceRepository,
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val lastState = processRepository.getStatus(context.device.id)
        if (lastState != ProcessStatus.IDLE) {
            return false
        }

        val setting = settingRepository.get()
        val scrcpyCommand = ScrcpyCommand(ScrcpyCommandFactory(setting.scrcpyLocation))

        return try {
            val process = scrcpyCommand.record(context, deviceRepository.createRecordPathForDesktop(context))
            processRepository.add(context.device.id, process, ProcessStatus.RECORDING) { onDestroy.invoke() }
            true
        } catch (e: Exception) {
            false
        }
    }
}

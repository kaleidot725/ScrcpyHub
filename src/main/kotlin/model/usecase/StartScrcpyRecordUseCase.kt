package model.usecase

import model.entity.Device
import model.entity.Message
import model.repository.MessageRepository
import model.repository.ProcessRepository
import model.repository.ProcessStatus
import model.repository.SettingRepository
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StartScrcpyRecordUseCase(
    private val settingRepository: SettingRepository,
    private val processRepository: ProcessRepository,
    private val messageRepository: MessageRepository
) {
    suspend fun execute(context: Device.Context, onDestroy: suspend () -> Unit): Boolean {
        val lastState = processRepository.getStatus(context.device.id)
        if (lastState != ProcessStatus.IDLE) {
            return false
        }

        try {
            val directory = createRecordDirectory()
            if (!File(directory).exists()) {
                messageRepository.notify(Message.Notify.FailedRecordingMovie(context))
                return false
            }

            val fileName = createRecordPath(directory, context)
            val scrcpyLocation = settingRepository.get().scrcpyLocation
            processRepository.addRecordingProcess(context, fileName, scrcpyLocation) {
                onDestroy.invoke()
            }
            messageRepository.notify(Message.Notify.StartRecordingMovie(context))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private suspend fun createRecordDirectory(): String {
        val screenRecordDirectory = settingRepository.get().screenRecordDirectory
        return if (screenRecordDirectory.isEmpty()) {
            "${System.getProperty("user.home")}/Desktop/"
        } else {
            if (screenRecordDirectory.endsWith("/")) {
                screenRecordDirectory
            } else {
                "${screenRecordDirectory}/"
            }
        }
    }

    private suspend fun createRecordPath(directory: String, context: Device.Context): String {
        val date = ZonedDateTime
            .now(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
        return "$directory${context.displayName}-$date.mp4"
    }
}

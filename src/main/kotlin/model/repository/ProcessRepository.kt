package model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import model.command.KillCommand
import model.command.ScrcpyCommand
import model.command.ScrcpyCommandCreator
import model.entity.Device
import java.util.Date

private data class ProcessState(
    val value: Process,
    val startTime: Date,
    val status: ProcessStatus
)

enum class ProcessStatus {
    IDLE,
    RUNNING,
    RECORDING
}

class ProcessRepository(
    val killCommand: KillCommand
) {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + Dispatchers.IO)

    fun addMirroringProcess(context: Device.Context, scrcpyLocation: String, onDestroy: (suspend () -> Unit)? = null) {
        val process = ScrcpyCommand(ScrcpyCommandCreator(scrcpyLocation)).run(context)
        processList[context.device.id] = ProcessState(process, Date(), ProcessStatus.RUNNING)
        scope.launch(Dispatchers.IO) {
            process.waitForRunning(MONITORING_DELAY)
            process.monitor(MONITORING_INTERVAL) {
                processList.remove(context.device.id)
                onDestroy?.invoke()
            }
        }
    }

    fun addRecordingProcess(
        context: Device.Context,
        fileName: String,
        commandLocation: String,
        onDestroy: (suspend () -> Unit)? = null
    ) {
        val process = ScrcpyCommand(ScrcpyCommandCreator(commandLocation)).record(context, fileName)
        processList[context.device.id] = ProcessState(process, Date(), ProcessStatus.RECORDING)
        scope.launch(Dispatchers.IO) {
            process.waitForRunning(MONITORING_DELAY)
            process.monitor(MONITORING_INTERVAL) {
                processList.remove(context.device.id)
                onDestroy?.invoke()
            }
        }
    }

    fun delete(key: String) {
        processList[key]?.let {
            killCommand.run(it.value.pid())
        }
        processList.remove(key)
    }

    fun getStatus(key: String): ProcessStatus {
        return processList[key]?.status ?: ProcessStatus.IDLE
    }

    private suspend fun Process.waitForRunning(interval: Long) {
        withTimeout(TIMEOUT) {
            while (!this@waitForRunning.isAlive) {
                delay(interval)
            }
        }
    }

    private suspend fun Process.monitor(interval: Long, onDestroy: suspend () -> Unit) {
        while (this.isAlive) {
            delay(interval)
        }
        onDestroy.invoke()
    }

    companion object {
        private const val TIMEOUT = 10000L
        private const val MONITORING_DELAY = 1000L
        private const val MONITORING_INTERVAL = 1000L
        private val processList: MutableMap<String, ProcessState> = mutableMapOf()
    }
}

package model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import model.command.ScrcpyCommand
import model.command.factory.ScrcpyCommandFactory
import model.entity.Device

private data class ProcessState(
    val value: Process,
    val status: ProcessStatus
)

enum class ProcessStatus {
    IDLE,
    RUNNING,
    RECORDING
}

class ProcessRepository {
    private val processList: MutableMap<String, ProcessState> = mutableMapOf()
    private val scope: CoroutineScope = MainScope()

    fun addMirroringProcess(context: Device.Context, scrcpyLocation: String, onDestroy: (suspend () -> Unit)? = null) {
        val process = ScrcpyCommand(ScrcpyCommandFactory(scrcpyLocation)).run(context)
        processList[context.device.id] = ProcessState(process, ProcessStatus.RUNNING)
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
        val process = ScrcpyCommand(ScrcpyCommandFactory(commandLocation)).record(context, fileName)
        processList[context.device.id] = ProcessState(process, ProcessStatus.RUNNING)
        scope.launch(Dispatchers.IO) {
            process.waitForRunning(MONITORING_DELAY)
            process.monitor(MONITORING_INTERVAL) {
                processList.remove(context.device.id)
                onDestroy?.invoke()
            }
        }
    }

    fun delete(key: String) {
        processList[key]?.value?.kill()
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

    private fun Process.kill() {
        Runtime.getRuntime().exec("kill -SIGINT ${this.pid()}").waitFor()
    }

    companion object {
        private const val TIMEOUT = 10000L
        private const val MONITORING_DELAY = 1000L
        private const val MONITORING_INTERVAL = 1000L
    }
}

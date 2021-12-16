package model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

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

    fun add(key: String, process: Process, status: ProcessStatus, onDestroy: (suspend () -> Unit)? = null) {
        processList[key] = ProcessState(process, status)
        scope.launch(Dispatchers.IO) {
            process.waitForRunning(MONITORING_DELAY)
            process.monitor(MONITORING_INTERVAL) { onDestroy?.invoke() }
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

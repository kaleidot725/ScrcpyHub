package model.repository

import kotlinx.coroutines.*

class ProcessRepository {
    private val processList: MutableMap<String, Process> = mutableMapOf()
    private val scope: CoroutineScope = MainScope()

    fun insert(key: String, process: Process, onDestroy: (suspend () -> Unit)? = null) {
        processList[key] = process
        scope.launch {
            process.waitForRunning(MONITORING_DELAY)
            process.monitor(key, MONITORING_INTERVAL) { onDestroy?.invoke() }
        }
    }

    fun any(key: String): Boolean {
        return processList[key] != null
    }

    fun delete(key: String) {
        processList[key]?.destroy()
        processList.remove(key)
    }

    fun deleteAll() {
        processList.values.forEach { it.destroy() }
        processList.clear()
    }

    private suspend fun Process.waitForRunning(interval: Long) {
        withTimeout(TIMEOUT) {
            while (!this@waitForRunning.isAlive) {
                delay(interval)
            }
        }
    }

    private suspend fun Process.monitor(key: String, interval: Long, onDestroy: suspend () -> Unit) {
        while (this.isAlive) {
            delay(interval)
        }

        delete(key)
        onDestroy.invoke()
    }

    companion object {
        private const val TIMEOUT = 10000L
        private const val MONITORING_DELAY = 1000L
        private const val MONITORING_INTERVAL = 1000L
    }
}
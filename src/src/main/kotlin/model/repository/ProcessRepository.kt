package model.repository

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.command.ScrcpyCommand

class ProcessRepository {
    private var process: Process? = null

    fun run(
        command: ScrcpyCommand,
        onDestroy: (suspend () -> Unit)? = null
    ): Boolean {
        return try {
            process?.destroy()
            process = command.run()
            MainScope().launch {
                process?.waitForRunning(1000)
                process?.monitor(1000) { onDestroy?.invoke() }
            }
            true
        } catch (e: Exception) {
            process = null
            false
        }
    }

    fun stop() {
        process?.destroy()
        process = null
    }

    private suspend fun Process.waitForRunning(interval: Long) {
        while (!this.isAlive) {
            delay(interval)
        }
    }

    private suspend fun Process.monitor(interval: Long, onDestroy: suspend () -> Unit) {
        while (this.isAlive) {
            delay(interval)
        }

        onDestroy.invoke()
    }
}
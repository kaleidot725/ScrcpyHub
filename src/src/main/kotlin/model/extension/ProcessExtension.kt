package model.extension

import kotlinx.coroutines.delay

suspend fun Process.waitForRunning(interval: Long) {
    while (!this.isAlive) {
        delay(interval)
    }
}

suspend fun Process.monitor(interval: Long, onDestroy: suspend () -> Unit) {
    while (this.isAlive) {
        delay(interval)
    }

    onDestroy.invoke()
}

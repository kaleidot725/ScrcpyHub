package model.service

import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.interactor.StopAdbInteractor
import java.io.File

object AdbServerService {
    private var latestBinaryPath: String? = null

    val isRunning get() = latestBinaryPath != null

    suspend fun restartAdbServer(path: String): Boolean {
        if (latestBinaryPath == path) return true
        stopAdbServer()
        return startAdbServer(path)
    }

    private suspend fun startAdbServer(path: String): Boolean {
        val result = StartAdbInteractor().execute(adbBinary = File(path))
        if (result) latestBinaryPath = path
        return result
    }

    private suspend fun stopAdbServer(): Boolean {
        val path = latestBinaryPath ?: return true
        val result = StopAdbInteractor().execute(File(path))
        if (result) latestBinaryPath = null
        return result
    }
}

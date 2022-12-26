package model.service

import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.interactor.StopAdbInteractor
import java.io.File

object AdbServerService {
    private var latestBinaryPath: String? = null

    val isRunning get() = latestBinaryPath != null

    suspend fun startAdbServer(path: String): Boolean {
        if (latestBinaryPath == path) return true
        if (isRunning) return false

        val result = StartAdbInteractor().execute(adbBinary = File(path))
        if (result) latestBinaryPath = path
        return result
    }

    suspend fun stopAdbServer(): Boolean {
        if (!isRunning) return false
        val result = StopAdbInteractor().execute(adbBinary = File(latestBinaryPath))
        if (result) latestBinaryPath = null
        return result
    }
}
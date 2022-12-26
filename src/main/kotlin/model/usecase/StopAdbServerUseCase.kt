package model.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.service.AdbServerService

class StopAdbServerUseCase {
    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext AdbServerService.stopAdbServer()
        }
    }
}

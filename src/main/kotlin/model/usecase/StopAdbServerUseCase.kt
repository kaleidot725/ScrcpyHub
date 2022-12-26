package model.usecase

import com.malinskiy.adam.interactor.StopAdbInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.repository.SettingRepository
import java.io.File

class StopAdbServerUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            val setting = settingRepository.get()
            return@withContext StopAdbInteractor().execute(adbBinary = File(setting.adbLocation))
        }
    }
}

package model.usecase

import com.malinskiy.adam.interactor.StartAdbInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.repository.SettingRepository
import java.io.File

class StartAdbServerUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {
            val setting = settingRepository.get()
            return@withContext StartAdbInteractor().execute(adbBinary = File(setting.adbLocation))
        }
    }
}

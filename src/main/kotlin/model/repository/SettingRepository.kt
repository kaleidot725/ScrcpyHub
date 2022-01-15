package model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.AppSetting
import model.os.OSContext
import java.io.File

class SettingRepository(private val osContext: OSContext) {
    suspend fun get(): AppSetting {
        return withContext(Dispatchers.IO) {
            load()
        }
    }

    suspend fun update(setting: AppSetting) {
        withContext(Dispatchers.IO) {
            createDir()
            write(setting)
        }
    }

    private fun write(setting: AppSetting) {
        try {
            File(osContext.settingPath + SETTING_FILE_NAME).outputStream().apply {
                this.write(Json.encodeToString(setting).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun load(): AppSetting {
        return try {
            val content = File(osContext.settingPath + SETTING_FILE_NAME).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            AppSetting()
        }
    }

    private fun createDir() {
        try {
            val file = File(osContext.settingPath)
            if (!file.exists()) file.mkdir()
        } catch (e: Exception) {
            return
        }
    }

    companion object {
        private const val SETTING_FILE_NAME = "setting.json"
    }
}

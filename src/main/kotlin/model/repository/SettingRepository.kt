package model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.Setting
import model.os.OSContext
import java.io.File

class SettingRepository(private val osContext: OSContext) {
    suspend fun get(): Setting {
        return withContext(Dispatchers.IO) {
            load()
        }
    }

    suspend fun update(setting: Setting) {
        withContext(Dispatchers.IO) {
            createDir()
            write(setting)
        }
    }

    private fun write(setting: Setting) {
        try {
            File(osContext.settingPath + SETTING_FILE_NAME).outputStream().apply {
                this.write(Json.encodeToString(setting).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun load(): Setting {
        return try {
            val content = File(osContext.settingPath + SETTING_FILE_NAME).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            Setting()
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

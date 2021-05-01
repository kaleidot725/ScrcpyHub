package model.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.Setting
import java.io.File

class SettingRepository {
    fun get(): Setting {
        return load()
    }

    fun update(setting: Setting) {
        write(setting)
    }

    private fun write(setting: Setting) {
        try {
            File(SETTING_FILE_NAME).outputStream().apply {
                this.write(Json.encodeToString(setting).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun load(): Setting {
        return try {
            val content = File(SETTING_FILE_NAME).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            Setting()
        }
    }

    companion object {
        private const val SETTING_FILE_NAME = "setting.json"
    }
}
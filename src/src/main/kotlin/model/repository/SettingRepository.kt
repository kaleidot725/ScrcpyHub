package model.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.entity.AppSetting
import model.utils.FileUtils

class SettingRepository(private val root: String) {
    fun get(): AppSetting {
        return load()
    }

    fun update(setting: AppSetting) {
        createDir()
        write(setting)
    }

    private fun write(setting: AppSetting) {
        try {
            FileUtils.createFileFile(root, SETTING_FILE_NAME).outputStream().apply {
                this.write(Json.encodeToString(setting).toByteArray())
                this.close()
            }
        } catch (e: Exception) {
            return
        }
    }

    private fun load(): AppSetting {
        return try {
            val content = FileUtils.createFileFile(root, SETTING_FILE_NAME).readText()
            Json.decodeFromString(string = content)
        } catch (e: Exception) {
            AppSetting()
        }
    }

    private fun createDir() {
        try {
            val file = FileUtils.createDirFile(root)
            if (!file.exists()) file.mkdir()
        } catch (e: Exception) {
            return
        }
    }

    companion object {
        private const val SETTING_FILE_NAME = "setting.json"
    }
}
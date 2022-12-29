package view.pages.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Setting
import model.entity.Theme
import model.usecase.FetchSettingUseCase
import model.usecase.UpdateSettingUseCase
import view.StateHolder

class SettingPageStateHolder(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase
) : StateHolder() {
    private val _adbLocation: MutableStateFlow<String> = MutableStateFlow("")
    val adbLocation: StateFlow<String> = _adbLocation

    private val _scrcpyLocation: MutableStateFlow<String> = MutableStateFlow("")
    val scrcpyLocation: StateFlow<String> = _scrcpyLocation

    private val _screenRecordDirectory: MutableStateFlow<String> = MutableStateFlow("")
    val screenRecordDirectory: StateFlow<String> = _screenRecordDirectory

    private val _screenshotDirectory: MutableStateFlow<String> = MutableStateFlow("")
    val screenshotDirectory: StateFlow<String> = _screenshotDirectory

    private val _theme: MutableStateFlow<Theme> = MutableStateFlow(Theme.LIGHT)
    val theme: StateFlow<Theme> = _theme
    val themes: StateFlow<List<Theme>> = MutableStateFlow(Theme.values().toList())

    init {
        coroutineScope.launch {
            val setting = fetchSettingUseCase.execute()
            _adbLocation.value = setting.adbLocation
            _scrcpyLocation.value = setting.scrcpyLocation
            _screenRecordDirectory.value = setting.screenRecordDirectory
            _screenshotDirectory.value = setting.screenshotDirectory
            _theme.value = setting.theme
        }
    }

    fun updateAdbLocation(location: String) {
        _adbLocation.value = location
    }

    fun updateScrcpyLocation(location: String) {
        _scrcpyLocation.value = location
    }

    fun updateScreenshotDirectory(directory: String) {
        _screenshotDirectory.value = directory
    }

    fun updateScreenRecordDirectory(directory: String) {
        _screenRecordDirectory.value = directory
    }

    fun updateTheme(theme: Theme) {
        _theme.value = theme
    }

    fun save(onSaved: () -> Unit) {
        coroutineScope.launch {
            updateSettingUseCase.execute(
                Setting(
                    adbLocation = _adbLocation.value,
                    theme = _theme.value,
                    scrcpyLocation = _scrcpyLocation.value,
                    screenRecordDirectory = _screenRecordDirectory.value,
                    screenshotDirectory = _screenshotDirectory.value
                )
            )
            onSaved.invoke()
        }
    }
}

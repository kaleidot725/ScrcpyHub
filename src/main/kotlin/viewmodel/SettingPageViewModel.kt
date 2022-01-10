package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.AppSetting
import model.entity.Theme
import model.usecase.FetchSettingUseCase
import model.usecase.UpdateSettingUseCase

class SettingPageViewModel(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase
) : ViewModel() {
    private val _adbLocation: MutableStateFlow<String> = MutableStateFlow("")
    val adbLocation: StateFlow<String> = _adbLocation

    private val _scrcpyLocation: MutableStateFlow<String> = MutableStateFlow("")
    val scrcpyLocation: StateFlow<String> = _scrcpyLocation

    private val _theme: MutableStateFlow<Theme?> = MutableStateFlow(null)
    val theme: StateFlow<Theme?> = _theme
    val themes: StateFlow<List<Theme>> = MutableStateFlow(Theme.values().toList())

    init {
        coroutineScope.launch {
            val setting = fetchSettingUseCase.execute()
            _adbLocation.value = setting.adbLocation
            _scrcpyLocation.value = setting.scrcpyLocation
            _theme.value = setting.theme
        }
    }

    fun updateAdbLocation(location: String) {
        _adbLocation.value = location
    }

    fun updateScrcpyLocation(location: String) {
        _scrcpyLocation.value = location
    }

    fun updateTheme(theme: Theme) {
        _theme.value = theme
    }

    fun save(onSaved: () -> Unit) {
        coroutineScope.launch {
            updateSettingUseCase.execute(
                AppSetting(
                    adbLocation = _adbLocation.value,
                    theme = _theme.value ?: Theme.SYNC_WITH_OS,
                    scrcpyLocation = _scrcpyLocation.value
                )
            )
            onSaved.invoke()
        }
    }
}

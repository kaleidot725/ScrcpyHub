package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.AppSetting
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

    init {
        coroutineScope.launch {
            val setting = fetchSettingUseCase.execute()
            _adbLocation.value = setting.adbLocation ?: ""
            _scrcpyLocation.value = setting.scrcpyLocation ?: ""
        }
    }

    fun updateAdbLocation(location: String) {
        _adbLocation.value = location
    }

    fun updateScrcpyLocation(location: String) {
        _scrcpyLocation.value = location
    }

    fun save(onSaved: () -> Unit) {
        coroutineScope.launch {
            updateSettingUseCase.execute(AppSetting(_adbLocation.value, _scrcpyLocation.value))
            onSaved.invoke()
        }
    }
}

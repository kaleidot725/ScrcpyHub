package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Setting
import model.usecase.FetchSettingUseCase
import model.usecase.UpdateSettingUseCase
import org.koin.core.component.inject

class SettingPageViewModel : ViewModel() {
    private val fetchSettingUseCase by inject<FetchSettingUseCase>()
    private val updateSettingUseCase by inject<UpdateSettingUseCase>()

    private val _adbLocation: MutableStateFlow<String> = MutableStateFlow("")
    val adbLocation: StateFlow<String> = _adbLocation

    private val _scrcpyLocation: MutableStateFlow<String> = MutableStateFlow("")
    val scrcpyLocation: StateFlow<String> = _scrcpyLocation

    init {
        val setting = fetchSettingUseCase.execute()
        _adbLocation.value = setting.adbLocation ?: ""
        _scrcpyLocation.value = setting.scrcpyLocation ?: ""
    }

    fun updateAdbLocation(location: String) {
        _adbLocation.value = location
    }

    fun updateScrcpyLocation(location: String) {
        _scrcpyLocation.value = location
    }

    fun save() {
        coroutineScope.launch {
            updateSettingUseCase.execute(Setting(_adbLocation.value, _scrcpyLocation.value))
        }
    }
}

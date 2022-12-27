package view

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.entity.Setting
import model.entity.Theme
import model.usecase.CheckSetupStatusUseCase
import model.usecase.FetchSettingUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import model.usecase.StartAdbServerUseCase
import model.usecase.StopAdbServerUseCase
import view.navigation.Navigation

class MainContentStateHolder(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val checkSetupStatusUseCase: CheckSetupStatusUseCase,
    private val getSystemDarkModeFlowUseCase: GetSystemDarkModeFlowUseCase,
    private val startAdbServerUseCase: StartAdbServerUseCase,
    private val stopAdbServerUseCase: StopAdbServerUseCase
) : StateHolder() {
    private val _navState: MutableStateFlow<Navigation> = MutableStateFlow(Navigation.DevicesPage)
    val navState: StateFlow<Navigation> = _navState

    private val _setting: MutableStateFlow<Setting> = MutableStateFlow(Setting())
    val setting: StateFlow<Setting> = _setting

    private val systemDarkMode: StateFlow<Boolean?> = getSystemDarkModeFlowUseCase()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), null)
    val isDarkMode: Flow<Boolean?> = _setting.combine(systemDarkMode) { setting, systemDarkMode ->
        systemDarkMode ?: return@combine null
        when (setting.theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
            Theme.SYNC_WITH_OS -> systemDarkMode
        }
    }

    override fun onStarted() {
        updateSetting()
        checkSetupStatus()
        restartAdbServer()
    }

    override fun onRefresh() {
        updateSetting()
        checkSetupStatus()
        restartAdbServer()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.launch { stopAdbServerUseCase() }
    }

    fun selectPage(page: Navigation) {
        _navState.value = page
    }

    private fun restartAdbServer() {
        coroutineScope.launch {
            stopAdbServerUseCase()
            startAdbServerUseCase()
        }
    }

    private fun updateSetting() {
        coroutineScope.launch {
            _setting.value = fetchSettingUseCase.execute()
        }
    }

    private fun checkSetupStatus() {
        coroutineScope.launch {
            checkSetupStatusUseCase()
        }
    }
}

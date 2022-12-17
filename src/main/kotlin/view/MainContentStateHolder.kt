package view

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import model.entity.Message
import model.entity.Setting
import model.entity.Theme
import model.usecase.FetchSettingUseCase
import model.usecase.GetMessageFlowUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import model.usecase.IsSetupCompletedUseCase
import view.navigation.Navigation
import view.resource.Strings.NOT_FOUND_ADB_COMMAND
import view.resource.Strings.NOT_FOUND_SCRCPY_COMMAND

class MainContentStateHolder(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val isSetupCompletedUseCase: IsSetupCompletedUseCase,
    private val getMessageFlowUseCase: GetMessageFlowUseCase,
    private val getSystemDarkModeFlowUseCase: GetSystemDarkModeFlowUseCase
) : StateHolder() {
    private val _navState: MutableStateFlow<Navigation> = MutableStateFlow(Navigation.DevicesPage)
    val navState: StateFlow<Navigation> = _navState

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _notifyMessage: MutableStateFlow<Message> = MutableStateFlow(Message.EmptyMessage)
    val notifyMessage: StateFlow<Message> = _notifyMessage

    private val setting: MutableStateFlow<Setting?> = MutableStateFlow(null)
    private val systemDarkMode: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isDarkMode: Flow<Boolean?> = setting.combine(systemDarkMode) { setting, systemDarkMode ->
        setting ?: return@combine null
        systemDarkMode ?: return@combine null
        when (setting.theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
            Theme.SYNC_WITH_OS -> systemDarkMode
        }
    }

    override fun onStarted() {
        initSetting()
        observeNotifyMessage()
        observeSystemDarkMode()
    }

    fun selectPage(page: Navigation) {
        _navState.value = page
    }

    fun refreshSetting() {
        coroutineScope.launch {
            setting.value = fetchSettingUseCase.execute()
            _errorMessage.value = when (isSetupCompletedUseCase.execute()) {
                IsSetupCompletedUseCase.Result.NOT_FOUND_SCRCPY_COMMAND -> NOT_FOUND_SCRCPY_COMMAND
                IsSetupCompletedUseCase.Result.NOT_FOUND_ADB_COMMAND -> NOT_FOUND_ADB_COMMAND
                else -> null
            }
        }
    }

    private fun observeNotifyMessage() {
        coroutineScope.launch {
            getMessageFlowUseCase.execute().collect {
                _notifyMessage.value = it
                delay(2000)
                _notifyMessage.value = Message.EmptyMessage
            }
        }
    }

    private fun observeSystemDarkMode() {
        coroutineScope.launch {
            getSystemDarkModeFlowUseCase().collect { systemDarkMode.value = it }
        }
    }

    private fun initSetting() {
        coroutineScope.launch(NonCancellable) {
            setting.value = fetchSettingUseCase.execute()
            delay(2000)
            _navState.value = Navigation.DevicesPage
        }
    }
}

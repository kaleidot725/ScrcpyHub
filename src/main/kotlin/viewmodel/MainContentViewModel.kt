package viewmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import model.entity.AppSetting
import model.entity.Message
import model.entity.Theme
import model.usecase.FetchSettingUseCase
import model.usecase.GetMessageFlowUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import model.usecase.IsSetupCompletedUseCase
import resource.Strings.NOT_FOUND_ADB_COMMAND
import resource.Strings.NOT_FOUND_SCRCPY_COMMAND
import view.page.Page


class MainContentViewModel(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val isSetupCompletedUseCase: IsSetupCompletedUseCase,
    private val getMessageFlowUseCase: GetMessageFlowUseCase,
    private val getSystemDarkModeFlowUseCase: GetSystemDarkModeFlowUseCase
) : ViewModel() {
    private val _selectedPages: MutableStateFlow<Page> = MutableStateFlow(Page.LoadingPage)
    val selectedPages: StateFlow<Page> = _selectedPages

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _notifyMessage: MutableStateFlow<Message> = MutableStateFlow(Message.EmptyMessage)
    val notifyMessage: StateFlow<Message> = _notifyMessage

    private val setting: MutableStateFlow<AppSetting?> = MutableStateFlow(null)
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

    fun selectPage(page: Page) {
        _selectedPages.value = page
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
        coroutineScope.launch {
            setting.value = fetchSettingUseCase.execute()
            delay(2000)
            _selectedPages.value = Page.DevicesPage
        }
    }
}

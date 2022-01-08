package viewmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    private val _selectedPages: MutableStateFlow<Page> = MutableStateFlow(Page.DevicesPage)
    val selectedPages: StateFlow<Page> = _selectedPages

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _notifyMessage: MutableStateFlow<Message> = MutableStateFlow(Message.EmptyMessage)
    val notifyMessage: StateFlow<Message> = _notifyMessage

    private var appSetting: AppSetting? = null
    private var systemDarkMode: Boolean = false

    private val _isDarkMode: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    init {
        runBlocking { refreshSetting() }
    }
    
    override fun onStarted() {
        observeNotifyMessage()
        observeSystemDarkMode()
    }

    fun selectPage(page: Page) {
        _selectedPages.value = page
    }

    fun refreshSetting() {
        coroutineScope.launch {
            appSetting = fetchSettingUseCase.execute()
            checkError()
            checkDarkMode()
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
            getSystemDarkModeFlowUseCase().collect { systemDarkMode = it }
            checkDarkMode()
        }
    }

    private fun checkError() {
        coroutineScope.launch {
            _errorMessage.value = when (isSetupCompletedUseCase.execute()) {
                IsSetupCompletedUseCase.Result.NOT_FOUND_SCRCPY_COMMAND -> NOT_FOUND_SCRCPY_COMMAND
                IsSetupCompletedUseCase.Result.NOT_FOUND_ADB_COMMAND -> NOT_FOUND_ADB_COMMAND
                else -> null
            }
        }
    }

    private fun checkDarkMode() {
        val theme = appSetting?.theme ?: return
        _isDarkMode.value = when (theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
            Theme.SYNC_WITH_OS -> systemDarkMode
        }
    }
}

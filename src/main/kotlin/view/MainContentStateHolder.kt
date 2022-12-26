package view

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.entity.Message
import model.entity.Setting
import model.entity.Theme
import model.usecase.CheckSetupStatusUseCase
import model.usecase.FetchSettingUseCase
import model.usecase.GetErrorMessageFlowUseCase
import model.usecase.GetNotifyMessageFlowUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import view.navigation.Navigation

class MainContentStateHolder(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val checkSetupStatusUseCase: CheckSetupStatusUseCase,
    private val getNotifyMessageFlowUseCase: GetNotifyMessageFlowUseCase,
    private val getSystemDarkModeFlowUseCase: GetSystemDarkModeFlowUseCase,
    private val getErrorMessageFlowUseCase: GetErrorMessageFlowUseCase
) : StateHolder() {
    private val _navState: MutableStateFlow<Navigation> = MutableStateFlow(Navigation.DevicesPage)
    val navState: StateFlow<Navigation> = _navState

    private val setting: MutableStateFlow<Setting?> = MutableStateFlow(null)
    private val systemDarkMode: StateFlow<Boolean?> = getSystemDarkModeFlowUseCase()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), null)
    val isDarkMode: Flow<Boolean?> = setting.combine(systemDarkMode) { setting, systemDarkMode ->
        setting ?: return@combine null
        systemDarkMode ?: return@combine null
        when (setting.theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
            Theme.SYNC_WITH_OS -> systemDarkMode
        }
    }

    private val notifyMessage: MutableStateFlow<List<Message.Notify>> = MutableStateFlow(emptyList())
    private val errorMessage: StateFlow<Set<Message.Error>> = getErrorMessageFlowUseCase().stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        emptySet()
    )
    val messages: StateFlow<List<Message>> = combine(errorMessage, notifyMessage) { error, notify ->
        notify + error
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), emptyList())

    override fun onStarted() {
        updateSetting()
        observeNotifyMessage()
    }

    override fun onRefresh() {
        updateSetting()
    }

    fun selectPage(page: Navigation) {
        _navState.value = page
    }

    private fun observeNotifyMessage() {
        coroutineScope.launch {
            getNotifyMessageFlowUseCase().collect { newNotifyMessage ->
                coroutineScope.launch {
                    notifyMessage.value = notifyMessage.value.toMutableList().apply { add(newNotifyMessage) }
                    delay(2000)
                    notifyMessage.value = notifyMessage.value.toMutableList().apply { remove(newNotifyMessage) }
                }
            }
        }
    }

    private fun updateSetting() {
        coroutineScope.launch(NonCancellable) {
            setting.value = fetchSettingUseCase.execute()
            checkSetupStatusUseCase()
        }
    }
}

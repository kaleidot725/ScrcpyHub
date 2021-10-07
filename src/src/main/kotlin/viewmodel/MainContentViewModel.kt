package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.usecase.IsSetupCompletedUseCase
import org.koin.core.component.inject
import resource.Navigation
import resource.Strings.NOT_FOUND_ADB_COMMAND
import resource.Strings.NOT_FOUND_SCRCPY_COMMAND

class MainContentViewModel : ViewModel() {
    private val isSetupCompletedUseCase: IsSetupCompletedUseCase by inject()

    private val _selectedPages: MutableStateFlow<Navigation.Root> = MutableStateFlow(Navigation.DEVICES_PAGE)
    val selectedPages: StateFlow<Navigation.Root> = _selectedPages

    private val _hasError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    override fun onStarted() {
        refresh()
    }

    fun refresh() {
        checkError()
    }

    fun selectPage(page: Navigation.Root) {
        _selectedPages.value = page
    }

    private fun checkError() {
        val result = isSetupCompletedUseCase.execute()
        _hasError.value = (result != IsSetupCompletedUseCase.Result.OK)
        _errorMessage.value = when (result) {
            IsSetupCompletedUseCase.Result.NOT_FOUND_SCRCPY_COMMAND -> NOT_FOUND_SCRCPY_COMMAND
            IsSetupCompletedUseCase.Result.NOT_FOUND_ADB_COMMAND -> NOT_FOUND_ADB_COMMAND
            else -> null
        }
    }
}
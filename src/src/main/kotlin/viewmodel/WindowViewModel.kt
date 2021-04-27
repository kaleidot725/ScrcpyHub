package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import resource.Navigation

class WindowViewModel : ViewModel() {
    val pages: StateFlow<List<String>> = MutableStateFlow(Navigation.PAGE_NAMES)

    private val _selectedPages: MutableStateFlow<String> = MutableStateFlow(Navigation.DEVICES_PAGE)
    val selectedPages: StateFlow<String> = _selectedPages

    fun selectPage(page: String) {
        _selectedPages.value = page
    }
}
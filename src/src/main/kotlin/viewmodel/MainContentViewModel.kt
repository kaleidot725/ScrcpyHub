package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import resource.Navigation

class MainContentViewModel : ViewModel() {
    val pages: StateFlow<List<Navigation.Root>> = MutableStateFlow(Navigation.PAGE_NAMES)

    private val _selectedPages: MutableStateFlow<Navigation.Root> = MutableStateFlow(Navigation.DEVICES_PAGE)
    val selectedPages: StateFlow<Navigation.Root> = _selectedPages

    fun selectPage(page: Navigation.Root) {
        _selectedPages.value = page
    }
}
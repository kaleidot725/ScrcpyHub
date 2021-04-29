package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import resource.Navigation
import resource.Root

class MainContentViewModel : ViewModel() {
    val pages: StateFlow<List<Root>> = MutableStateFlow(Navigation.PAGE_NAMES)

    private val _selectedPages: MutableStateFlow<Root> = MutableStateFlow(Navigation.DEVICES_PAGE)
    val selectedPages: StateFlow<Root> = _selectedPages

    fun selectPage(page: Root) {
        _selectedPages.value = page
    }
}
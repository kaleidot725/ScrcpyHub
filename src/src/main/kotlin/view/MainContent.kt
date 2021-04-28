package view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resource.Colors
import resource.Navigation
import view.extention.onCreated
import view.extention.onDestroyed
import view.page.SettingPage
import view.tab.PageTab
import viewmodel.MainContentViewModel

@Composable
fun MainContent(mainContentViewModel: MainContentViewModel = MainContentViewModel()) {
    onCreated(mainContentViewModel)
    onDrawWindow(mainContentViewModel)
    onDestroyed(mainContentViewModel)
}

@Composable
private fun onDrawWindow(viewModel: MainContentViewModel) {
    val pages: List<String> by viewModel.pages.collectAsState()
    val selectedPages: String by viewModel.selectedPages.collectAsState()

    MainTheme {
        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            Column {
                PageTab(pages, selectedPages, onSelect = { viewModel.selectPage(it) })

                Spacer(modifier = Modifier.height(8.dp))

                Crossfade(selectedPages, animationSpec = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.DEVICES_PAGE -> DevicesPage()
                        Navigation.SETTING_PAGE -> SettingPage()
                    }
                }
            }
        }
    }
}


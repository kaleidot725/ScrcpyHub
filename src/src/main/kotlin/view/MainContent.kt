package view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Colors
import resource.Navigation
import resource.Strings.SETUP
import view.extention.onInitialize
import view.page.SettingPage
import view.tab.PageTab
import viewmodel.MainContentViewModel

@Composable
fun MainContent(mainContentViewModel: MainContentViewModel = MainContentViewModel()) {
    onInitialize(mainContentViewModel)
    onDrawWindow(mainContentViewModel)
}

@Composable
private fun onDrawWindow(viewModel: MainContentViewModel) {
    val pages: List<Navigation.Root> by viewModel.pages.collectAsState()
    val selectedPages: Navigation.Root by viewModel.selectedPages.collectAsState()
    val hasError: Boolean by viewModel.hasError.collectAsState()
    val errorMessage: String? by viewModel.errorMessage.collectAsState()

    MainTheme {
        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            Column {
                PageTab(
                    pages,
                    selectedPages,
                    onSelect = {
                        viewModel.selectPage(it)
                        viewModel.refresh()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Crossfade(selectedPages, animationSpec = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.DEVICES_PAGE -> DevicesPage()
                        Navigation.SETTING_PAGE -> SettingPage(onSaved = { viewModel.refresh() }
                        )
                    }
                }
            }

            if (hasError) {
                if (errorMessage != null) {
                    Snackbar(modifier = Modifier.padding(8.dp).align(Alignment.BottomCenter)) {
                        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                            Row(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
                                Text(errorMessage ?: "", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    SETUP,
                                    fontSize = 16.sp,
                                    color = resource.Colors.NAVY,
                                    modifier = Modifier.clickable { viewModel.selectPage(Navigation.SETTING_PAGE) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Message
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import view.extention.onInitialize
import view.page.DevicePage
import view.page.Page
import view.page.SettingPage
import viewmodel.DevicePageViewModel
import viewmodel.DevicesPageViewModel
import viewmodel.MainContentViewModel
import viewmodel.SettingPageViewModel

@Composable
fun MainContent(windowScope: WindowScope, mainContentViewModel: MainContentViewModel) {
    onInitialize(mainContentViewModel)
    onDrawWindow(windowScope, mainContentViewModel)
}

@Composable
private fun onDrawWindow(windowScope: WindowScope, viewModel: MainContentViewModel) {
    val isDarkMode: Boolean? by viewModel.isDarkMode.collectAsState(null)
    MainTheme(isDarkMode = isDarkMode ?: true) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            MainPages(windowScope, viewModel)
            MainSnacks(viewModel)
        }
    }
}

@Composable
private fun MainPages(windowScope: WindowScope, mainViewModel: MainContentViewModel) {
    val selectedPages: Page by mainViewModel.selectedPages.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val page = selectedPages) {
            Page.LoadingPage -> {
                LoadingPage(windowScope = windowScope)
            }
            Page.DevicesPage -> {
                val devicesPageViewModel by remember {
                    val viewModel by inject<DevicesPageViewModel>(clazz = DevicesPageViewModel::class.java)
                    mutableStateOf(viewModel)
                }

                DevicesPage(
                    windowScope = windowScope,
                    devicesPageViewModel = devicesPageViewModel,
                    onNavigateSetting = { mainViewModel.selectPage(Page.SettingPage) },
                    onNavigateDevice = { mainViewModel.selectPage(Page.DevicePage(it)) }
                )
            }
            Page.SettingPage -> {
                val settingPageViewModel by remember {
                    val viewModel by inject<SettingPageViewModel>(clazz = SettingPageViewModel::class.java)
                    mutableStateOf(viewModel)
                }

                SettingPage(
                    windowScope = windowScope,
                    settingPageViewModel = settingPageViewModel,
                    onNavigateDevices = { mainViewModel.selectPage(Page.DevicesPage) },
                    onSaved = { mainViewModel.refreshSetting() }
                )
            }
            is Page.DevicePage -> {
                val devicePageViewModel by remember {
                    val viewModel by inject<DevicePageViewModel>(clazz = DevicePageViewModel::class.java) {
                        parametersOf(page.context)
                    }
                    mutableStateOf(viewModel)
                }

                DevicePage(
                    windowScope = windowScope,
                    deviceViewModel = devicePageViewModel,
                    onNavigateDevices = { mainViewModel.selectPage(Page.DevicesPage) }
                )
            }
        }
    }
}

@Composable
private fun MainSnacks(viewModel: MainContentViewModel) {
    val errorMessage: String? by viewModel.errorMessage.collectAsState()
    val notifyMessage: Message by viewModel.notifyMessage.collectAsState()

    val notifyMessageState = remember { MutableTransitionState(false) }
    val errorMessageState = remember { MutableTransitionState(false) }

    notifyMessageState.targetState = notifyMessage != Message.EmptyMessage
    errorMessageState.targetState = errorMessage != null

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            AnimatedVisibility(notifyMessageState, enter = fadeIn(), exit = fadeOut()) {
                Snackbar(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Row(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
                            Text(notifyMessage.toStringMessage(), style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
            AnimatedVisibility(errorMessageState, enter = fadeIn(), exit = fadeOut()) {
                if (errorMessage != null) {
                    Snackbar(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text(
                            errorMessage!!,
                            style = MaterialTheme.typography.button,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        )
                    }
                }
            }
        }
    }
}

private fun Message.toStringMessage(): String {
    return when (this) {
        Message.EmptyMessage -> ""
        is Message.SuccessToSaveScreenshot -> "Success to save ${this.context.displayName} Screenshot!"
        is Message.FailedToSaveScreenshot -> "Failed to save ${this.context.displayName} Screenshot!"
    }
}

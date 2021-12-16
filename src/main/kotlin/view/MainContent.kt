package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Message
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import resource.Colors
import resource.Page
import resource.Strings.SETUP
import view.extention.onInitialize
import view.page.DevicePage
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
    MainTheme {
        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            MainPages(windowScope, viewModel)
            MainSnacks(viewModel)
        }
    }
}

@Composable
private fun MainPages(windowScope: WindowScope, viewModel: MainContentViewModel) {
    val selectedPages: Page by viewModel.selectedPages.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val page = selectedPages) {
            Page.DevicesPage -> {
                val devicesPageViewModel by inject<DevicesPageViewModel>(clazz = DevicesPageViewModel::class.java)
                DevicesPage(
                    windowScope = windowScope,
                    devicesPageViewModel = devicesPageViewModel,
                    onNavigateSetting = { viewModel.selectPage(Page.SettingPage) },
                    onNavigateDevice = { viewModel.selectPage(Page.DevicePage(it)) }
                )
            }
            Page.SettingPage -> {
                val settingPageViewModel by inject<SettingPageViewModel>(clazz = SettingPageViewModel::class.java)
                SettingPage(
                    windowScope = windowScope,
                    settingPageViewModel = settingPageViewModel,
                    onNavigateDevices = { viewModel.selectPage(Page.DevicesPage) },
                    onSaved = { viewModel.checkError() }
                )
            }
            is Page.DevicePage -> {
                val devicePageViewModel by inject<DevicePageViewModel>(clazz = DevicePageViewModel::class.java) {
                    parametersOf(page.context)
                }
                DevicePage(
                    windowScope = windowScope,
                    deviceViewModel = devicePageViewModel,
                    onNavigateDevices = { viewModel.selectPage(Page.DevicesPage) }
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
                Snackbar(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Row(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
                            Text(errorMessage ?: "", style = MaterialTheme.typography.button)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                SETUP,
                                style = MaterialTheme.typography.button,
                                color = Colors.NAVY,
                                modifier = Modifier.clickable { viewModel.selectPage(Page.SettingPage) }
                            )
                        }
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

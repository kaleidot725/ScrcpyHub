package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import model.entity.Message
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import view.navigation.NavState
import view.page.DevicePage
import view.page.SettingPage
import view.pages.DevicesPage
import view.resource.Colors
import view.resource.Images
import viewmodel.DevicePageViewModel
import viewmodel.DevicesPageViewModel
import viewmodel.MainContentViewModel
import viewmodel.SettingPageViewModel
import viewmodel.ViewModel

@Composable
fun AppWindow(
    onCloseRequest: () -> Unit,
    state: WindowState,
    alwaysOnTop: Boolean,
    content: @Composable FrameWindowScope.() -> Unit
) {
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        resizable = false,
        undecorated = true,
        transparent = true,
        alwaysOnTop = alwaysOnTop,
        icon = painterResource(Images.DEVICE),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Colors.window_border)
        ) { content.invoke(this) }
    }
}

@Composable
fun MainContent(windowScope: WindowScope, mainContentViewModel: MainContentViewModel) {
    DisposableEffect(mainContentViewModel) {
        mainContentViewModel.onStarted()
        onDispose {
            mainContentViewModel.onCleared()
        }
    }

    val isDarkMode: Boolean? by mainContentViewModel.isDarkMode.collectAsState(null)
    MainTheme(isDarkMode = isDarkMode ?: true) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            MainPages(windowScope, mainContentViewModel)
            MainSnacks(mainContentViewModel)
        }
    }
}

@Composable
private fun MainPages(windowScope: WindowScope, mainViewModel: MainContentViewModel) {
    val navState: NavState by mainViewModel.navState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val page = navState) {
            NavState.LoadingPage -> {
                LoadingPage()
            }
            NavState.DevicesPage -> {
                val devicesPageViewModel by remember {
                    val viewModel by inject<DevicesPageViewModel>(clazz = DevicesPageViewModel::class.java)
                    mutableStateOf(viewModel)
                }
                DevicesPage(
                    windowScope = windowScope,
                    devicesPageViewModel = devicesPageViewModel,
                    onNavigateSetting = { mainViewModel.selectPage(NavState.SettingPage) },
                    onNavigateDevice = { mainViewModel.selectPage(NavState.DevicePage(it)) }
                )
            }
            NavState.SettingPage -> {
                val settingPageViewModel by remember {
                    val viewModel by inject<SettingPageViewModel>(clazz = SettingPageViewModel::class.java)
                    mutableStateOf(viewModel)
                }
                SettingPage(
                    windowScope = windowScope,
                    settingPageViewModel = settingPageViewModel,
                    onNavigateDevices = { mainViewModel.selectPage(NavState.DevicesPage) },
                    onSaved = { mainViewModel.refreshSetting() }
                )
            }
            is NavState.DevicePage -> {
                val devicePageViewModel by remember {
                    val viewModel by inject<DevicePageViewModel>(clazz = DevicePageViewModel::class.java) {
                        parametersOf(page.context)
                    }
                    mutableStateOf(viewModel)
                }
                DevicePage(
                    windowScope = windowScope,
                    deviceViewModel = devicePageViewModel,
                    onNavigateDevices = { mainViewModel.selectPage(NavState.DevicesPage) }
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
        is Message.StartRecordingMovie -> "Start recording movie on ${this.context.displayName}"
        is Message.StopRecordingMovie -> "Stop recording movie on ${this.context.displayName}"
        is Message.FailedRecordingMovie -> "Failed recording movie on ${this.context.displayName}"
    }
}
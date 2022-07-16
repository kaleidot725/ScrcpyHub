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
import androidx.compose.runtime.DisposableEffect
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
import view.navigation.Navigation
import view.pages.DevicesPage
import view.pages.DevicesPageStateHolder
import view.pages.device.DevicePage
import view.pages.device.DevicePageStateHolder
import view.pages.setting.SettingPage
import view.pages.setting.SettingPageStateHolder

@Composable
fun MainContent(windowScope: WindowScope, mainStateHolder: MainContentStateHolder) {
    DisposableEffect(mainStateHolder) {
        mainStateHolder.onStarted()
        onDispose {
            mainStateHolder.onCleared()
        }
    }

    val isDarkMode: Boolean? by mainStateHolder.isDarkMode.collectAsState(null)
    MainTheme(isDarkMode = isDarkMode ?: true) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            MainPages(windowScope, mainStateHolder)
            MainSnacks(mainStateHolder)
        }
    }
}

@Composable
private fun MainPages(windowScope: WindowScope, mainStateHolder: MainContentStateHolder) {
    val navigation: Navigation by mainStateHolder.navState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val page = navigation) {
            Navigation.LoadingPage -> {
                LoadingPage()
            }
            Navigation.DevicesPage -> {
                val stateHolder by remember {
                    val stateHolder by inject<DevicesPageStateHolder>(clazz = DevicesPageStateHolder::class.java)
                    mutableStateOf(stateHolder)
                }
                DevicesPage(
                    windowScope = windowScope,
                    stateHolder = stateHolder,
                    onNavigateSetting = { mainStateHolder.selectPage(Navigation.SettingPage) },
                    onNavigateDevice = { mainStateHolder.selectPage(Navigation.DevicePage(it)) }
                )
            }
            Navigation.SettingPage -> {
                val stateHolder by remember {
                    val viewModel by inject<SettingPageStateHolder>(clazz = SettingPageStateHolder::class.java)
                    mutableStateOf(viewModel)
                }
                SettingPage(
                    windowScope = windowScope,
                    stateHolder = stateHolder,
                    onNavigateDevices = { mainStateHolder.selectPage(Navigation.DevicesPage) },
                    onSaved = { mainStateHolder.refreshSetting() }
                )
            }
            is Navigation.DevicePage -> {
                val devicePageViewModel by remember {
                    val stateHolder by inject<DevicePageStateHolder>(clazz = DevicePageStateHolder::class.java) {
                        parametersOf(page.context)
                    }
                    mutableStateOf(stateHolder)
                }
                DevicePage(
                    windowScope = windowScope,
                    stateHolder = devicePageViewModel,
                    onNavigateDevices = { mainStateHolder.selectPage(Navigation.DevicesPage) }
                )
            }
        }
    }
}

@Composable
private fun MainSnacks(viewModel: MainContentStateHolder) {
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

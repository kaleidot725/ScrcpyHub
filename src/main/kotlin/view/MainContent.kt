package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import view.pages.device.DevicePage
import view.pages.device.DevicePageStateHolder
import view.pages.devices.DevicesPage
import view.pages.devices.DevicesPageStateHolder
import view.pages.setting.SettingPage
import view.pages.setting.SettingPageStateHolder
import view.resource.MainTheme

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
            EventMessageList(mainStateHolder)
        }
    }
}

@Composable
private fun MainPages(windowScope: WindowScope, mainStateHolder: MainContentStateHolder) {
    val navigation: Navigation by mainStateHolder.navState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        val devicePageStateHolder by remember {
            val stateHolder by inject<DevicesPageStateHolder>(clazz = DevicesPageStateHolder::class.java)
            mutableStateOf(stateHolder)
        }

        DevicesPage(
            windowScope = windowScope,
            stateHolder = devicePageStateHolder,
            onNavigateSetting = { mainStateHolder.selectPage(Navigation.SettingPage) },
            onNavigateDevice = { mainStateHolder.selectPage(Navigation.DevicePage(it)) }
        )

        val settingPage = navigation as? Navigation.SettingPage
        AnimatedVisibility(
            visible = settingPage != null,
            enter = slideInHorizontally(initialOffsetX = { return@slideInHorizontally windowScope.window.width }),
            exit = slideOutHorizontally(targetOffsetX = { return@slideOutHorizontally windowScope.window.width })
        ) {
            val stateHolder by remember {
                val viewModel by inject<SettingPageStateHolder>(clazz = SettingPageStateHolder::class.java)
                mutableStateOf(viewModel)
            }

            SettingPage(
                windowScope = windowScope,
                stateHolder = stateHolder,
                onNavigateDevices = { mainStateHolder.selectPage(Navigation.DevicesPage) },
                onSaved = {
                    devicePageStateHolder.onRefresh()
                    mainStateHolder.onRefresh()
                }
            )
        }

        val devicePage = navigation as? Navigation.DevicePage
        AnimatedVisibility(
            visible = devicePage != null,
            enter = slideInHorizontally(initialOffsetX = { return@slideInHorizontally windowScope.window.width }),
            exit = slideOutHorizontally(targetOffsetX = { return@slideOutHorizontally windowScope.window.width })
        ) {
            val devicePageViewModel by remember {
                val stateHolder by inject<DevicePageStateHolder>(clazz = DevicePageStateHolder::class.java) {
                    parametersOf(devicePage!!.context)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.EventMessageList(viewModel: MainContentStateHolder) {
    val messages: List<Message> by viewModel.messages.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp).align(Alignment.BottomCenter)
    ) {
        items(messages, key = { it.uuid }) {
            val backgroundColor = when (it) {
                is Message.Error -> MaterialTheme.colors.error
                else -> MaterialTheme.colors.primary
            }
            val textColor = when (it) {
                is Message.Error -> MaterialTheme.colors.onError
                else -> MaterialTheme.colors.onPrimary
            }
            Card(backgroundColor = backgroundColor) {
                Text(
                    text = it.toUIMessage(),
                    color = textColor,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                )
            }
        }
    }
}

private fun Message.toUIMessage(): String {
    return when (this) {
        Message.Error.NotFoundAdbBinary -> "Not found adb binary,\nPlease setup adb binary location"
        Message.Error.NotFoundScrcpyBinary -> "Not found scrcpy binary,\nPlease setup scrcpy binary location"
        is Message.Notify.FailedToSaveScreenshot -> "Failed to save ${this.context.displayName} Screenshot!"
        is Message.Notify.StartRecordingMovie -> "Start recording movie on ${this.context.displayName}"
        is Message.Notify.StopRecordingMovie -> "Stop recording movie on ${this.context.displayName}"
        is Message.Notify.FailedRecordingMovie -> "Failed recording movie on ${this.context.displayName}"
        is Message.Notify.SuccessToSaveScreenshot -> "Success to save ${this.context.displayName} Screenshot!"
        is Message.Notify.StartMirroring -> "Start mirroring on ${this.context.displayName}"
        is Message.Notify.StopMirroring -> "Stop mirroring on ${this.context.displayName}"
        is Message.Notify.FailedMirroring -> "Failed mirroring on ${this.context.displayName}"
    }
}

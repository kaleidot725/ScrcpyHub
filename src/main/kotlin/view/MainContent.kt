package view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowScope
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import view.navigation.Navigation
import view.pages.device.DevicePage
import view.pages.device.DevicePageStateHolder
import view.pages.devices.DevicesPage
import view.pages.devices.DevicesPageForMini
import view.pages.devices.DevicesPageStateHolder
import view.pages.setting.SettingPage
import view.pages.setting.SettingPageStateHolder
import view.resource.Colors
import view.resource.MainTheme

@Composable
fun MainContent(
    windowScope: WindowScope,
    enableMiniMode: Boolean,
    mainStateHolder: MainContentStateHolder,
) {
    DisposableEffect(mainStateHolder) {
        mainStateHolder.onStarted()
        onDispose {
            mainStateHolder.onCleared()
        }
    }

    val isDarkMode: Boolean? by mainStateHolder.isDarkMode.collectAsState(null)
    MainTheme(isDarkMode = isDarkMode ?: true) {
        MainPages(windowScope, enableMiniMode, mainStateHolder)
    }
}

@Composable
private fun MainPages(
    windowScope: WindowScope,
    enableMiniMode: Boolean,
    mainStateHolder: MainContentStateHolder,
) {
    val navigation: Navigation by mainStateHolder.navState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        val devicesPageStateHolder by remember {
            val stateHolder by inject<DevicesPageStateHolder>(clazz = DevicesPageStateHolder::class.java)
            mutableStateOf(stateHolder)
        }

        if (enableMiniMode) {
            DevicesPageForMini(
                windowScope = windowScope,
                stateHolder = devicesPageStateHolder,
                onNavigateSetting = { mainStateHolder.selectPage(Navigation.SettingPage) },
                onNavigateDevice = { mainStateHolder.selectPage(Navigation.DevicePage(it)) },
            )
        } else {
            DevicesPage(
                windowScope = windowScope,
                stateHolder = devicesPageStateHolder,
                onNavigateSetting = { mainStateHolder.selectPage(Navigation.SettingPage) },
                onNavigateDevice = { mainStateHolder.selectPage(Navigation.DevicePage(it)) },
            )
        }

        val settingPage = navigation as? Navigation.SettingPage
        AnimatedVisibility(
            visible = settingPage != null,
            enter = slideInVertically(initialOffsetY = { return@slideInVertically windowScope.window.height }),
            exit = slideOutVertically(targetOffsetY = { return@slideOutVertically windowScope.window.height * 2 }),
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
                    mainStateHolder.onRefresh()
                },
            )
        }

        val devicePage = navigation as? Navigation.DevicePage
        AnimatedVisibility(
            visible = devicePage != null,
            enter = slideInVertically(initialOffsetY = { return@slideInVertically windowScope.window.height }),
            exit = slideOutVertically(targetOffsetY = { return@slideOutVertically windowScope.window.height * 2 }),
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
                onNavigateDevices = {
                    mainStateHolder.selectPage(Navigation.DevicesPage)
                    devicesPageStateHolder.onRefresh()
                },
            )
        }
    }
}

package view.pages.device

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.WindowScope
import view.components.DeviceSetting
import view.parts.SubPageHeader
import view.templates.MainLayout

@Composable
fun DevicePage(
    windowScope: WindowScope,
    stateHolder: DevicePageStateHolder,
    onNavigateDevices: (() -> Unit)? = null
) {
    val state by stateHolder.state.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    MainLayout(header = {
        SubPageHeader(
            windowScope = windowScope,
            title = state.titleName,
            onCancel = { onNavigateDevices?.invoke() },
            onSave = {
                stateHolder.viewAction.save()
                onNavigateDevices?.invoke()
            },
            savable = state.savable,
        )
    }, content = {
        DeviceSetting(
            name = state.editName,
            onUpdateName = { stateHolder.viewAction.updateName(it) },
            maxSize = state.maxSize,
            onUpdateMaxSize = { stateHolder.viewAction.updateMaxSize(it) },
            maxSizeError = state.maxSizeError,
            maxFrameRate = state.maxFrameRate,
            onUpdateFrameRate = { stateHolder.viewAction.updateMaxFrameRate(it) },
            maxFrameRateError = state.maxFrameRateError,
            bitrate = state.bitrate,
            onUpdateBitrate = { stateHolder.viewAction.updateBitrate(it) },
            bitrateError = state.bitrateError,
            lockOrientation = state.lockOrientation,
            lockOrientations = state.lockOrientations,
            onUpdateLockOrientation = { stateHolder.viewAction.updateLockOrientation(it) }
        )
    })
}

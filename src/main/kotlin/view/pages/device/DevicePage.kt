package view.pages.device

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import view.components.DeviceSetting
import view.parts.PageHeader
import view.resource.Images
import view.templates.HeaderAndContent

@Composable
fun DevicePage(
    windowScope: WindowScope,
    stateHolder: DevicePageStateHolder,
    onNavigateDevices: (() -> Unit)? = null
) {
    val titleName: String by stateHolder.titleName.collectAsState()
    val name: String by stateHolder.editName.collectAsState()
    val maxSize: String by stateHolder.maxSize.collectAsState()
    val maxSizeError: String by stateHolder.maxSizeError.collectAsState()
    val savable: Boolean by stateHolder.savable.collectAsState()
    val maxFrameRate: String by stateHolder.maxFrameRate.collectAsState()
    val maxFrameRateError: String by stateHolder.maxFrameRateError.collectAsState()
    val bitrate: String by stateHolder.bitrate.collectAsState()
    val bitrateError: String by stateHolder.bitrateError.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    HeaderAndContent(header = {
        PageHeader(windowScope = windowScope, title = titleName, optionContent = {
            Image(
                painter = painterResource(Images.CLOSE),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.wrapContentWidth().height(18.dp).clickable { onNavigateDevices?.invoke() }
            )
        })
    }, content = {
        DeviceSetting(
            name = name,
            onUpdateName = { stateHolder.updateName(it) },
            maxSize = maxSize,
            onUpdateMaxSize = { stateHolder.updateMaxSize(it) },
            maxSizeError = maxSizeError,
            maxFrameRate = maxFrameRate,
            onUpdateFrameRate = { stateHolder.updateMaxFrameRate(it) },
            maxFrameRateError = maxFrameRateError,
            bitrate = bitrate,
            onUpdateBitrate = { stateHolder.updateBitrate(it) },
            bitrateError = bitrateError,
            savable = savable,
            onSave = {
                stateHolder.save()
                onNavigateDevices?.invoke()
            }
        )
    })
}

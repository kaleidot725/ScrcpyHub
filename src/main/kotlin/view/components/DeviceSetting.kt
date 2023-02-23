package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.entity.Device
import view.parts.DropDownSelector
import view.parts.TextFieldAndError
import view.parts.TitleAndCheckButton
import view.resource.Strings
import view.resource.Strings.DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_BORDERLESS_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_BORDERLESS_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_FULLSCREEN_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_FULLSCREEN_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_180
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_COUNTER_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_NATURAL
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_NONE
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_TITLE

@Composable
fun DeviceSetting(
    name: String,
    onUpdateName: (String) -> Unit,
    maxSize: String,
    onUpdateMaxSize: (String) -> Unit,
    maxSizeError: String,
    maxFrameRate: String,
    onUpdateFrameRate: (String) -> Unit,
    maxFrameRateError: String,
    bitrate: String,
    onUpdateBitrate: (String) -> Unit,
    bitrateError: String,
    lockOrientation: Device.Context.LockOrientation,
    lockOrientations: List<Device.Context.LockOrientation>,
    onUpdateLockOrientation: (Device.Context.LockOrientation) -> Unit,
) {
    val scrollState = rememberScrollState()

    Box {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {
            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.DEVICE_PAGE_EDIT_DEVICE_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_NAME_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_NAME_DETAILS,
                        inputText = name,
                        onUpdateInputText = { onUpdateName(it) },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.DEVICE_PAGE_EDIT_VIDEO_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS,
                        inputText = maxSize,
                        onUpdateInputText = { onUpdateMaxSize(it) },
                        error = maxSizeError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_DETAILS,
                        inputText = maxFrameRate,
                        onUpdateInputText = { onUpdateFrameRate(it) },
                        error = maxFrameRateError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_MAX_BITRATE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_MAX_BITRATE_DETAILS,
                        inputText = bitrate,
                        onUpdateInputText = { onUpdateBitrate(it) },
                        error = bitrateError,
                        modifier = Modifier.padding(8.dp)
                    )

                    DropDownSelector(
                        label = DEVICE_PAGE_EDIT_ORIENTATION_TITLE,
                        selectedItem = lockOrientation.toTitle(),
                        items = lockOrientations.map { it.toTitle() },
                        onSelect = { title ->
                            val item = lockOrientations.firstOrNull { it.toTitle() == title } ?: return@DropDownSelector
                            onUpdateLockOrientation(item)
                        },
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                    )
                }
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.DEVICE_PAGE_EDIT_WINDOW_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    var borderless by remember { mutableStateOf(false) }
                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_BORDERLESS_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_BORDERLESS_DETAILS,
                        value = borderless,
                        onSelect = { borderless = it },
                        modifier = Modifier.padding(8.dp)
                    )

                    var alwaysOnTop by remember { mutableStateOf(false) }
                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_DETAILS,
                        value = alwaysOnTop,
                        onSelect = { alwaysOnTop = it },
                        modifier = Modifier.padding(8.dp)
                    )

                    var fullScreen by remember { mutableStateOf(false) }
                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_FULLSCREEN_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_FULLSCREEN_DETAILS,
                        value = fullScreen,
                        onSelect = { fullScreen = it },
                        modifier = Modifier.padding(8.dp)
                    )

                    DropDownSelector(
                        label = DEVICE_PAGE_EDIT_ROTATION_TITLE,
                        selectedItem = lockOrientation.toTitle(),
                        items = lockOrientations.map { it.toTitle() },
                        onSelect = { title ->
                            val item = lockOrientations.firstOrNull { it.toTitle() == title } ?: return@DropDownSelector
                            onUpdateLockOrientation(item)
                        },
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                    )
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }
}

private fun Device.Context.LockOrientation.toTitle(): String {
    return when (this) {
        Device.Context.LockOrientation.NONE -> DEVICE_PAGE_EDIT_ORIENTATION_NONE
        Device.Context.LockOrientation.NATURAL -> DEVICE_PAGE_EDIT_ORIENTATION_NATURAL
        Device.Context.LockOrientation.COUNTER_CLOCK_WISE_90 -> DEVICE_PAGE_EDIT_ORIENTATION_COUNTER_CLOCK_WISE_90
        Device.Context.LockOrientation.CLOCK_WISE_180 -> DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_180
        Device.Context.LockOrientation.CLOCK_WISE_90 -> DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_90
    }
}

@Preview
@Composable
private fun DeviceSetting_Savable_True_Preview() {
    DeviceSetting(
        name = "CUSTOM NAME",
        onUpdateName = {},
        maxSize = "1200",
        onUpdateMaxSize = {},
        maxSizeError = "",
        maxFrameRate = "60",
        onUpdateFrameRate = {},
        maxFrameRateError = "",
        bitrate = "1000",
        onUpdateBitrate = {},
        bitrateError = "",
        lockOrientation = Device.Context.LockOrientation.NONE,
        lockOrientations = Device.Context.LockOrientation.values().toList(),
        onUpdateLockOrientation = {}
    )
}

@Preview
@Composable
private fun DeviceSetting_Savable_False_Preview() {
    DeviceSetting(
        name = "CUSTOM NAME",
        onUpdateName = {},
        maxSize = "ERROR VALUE",
        onUpdateMaxSize = {},
        maxSizeError = "INVALID MAX SIZE",
        maxFrameRate = "ERROR VALUE",
        onUpdateFrameRate = {},
        maxFrameRateError = "INVALID MAX FRAME RATE",
        bitrate = "ERROR VALUE",
        onUpdateBitrate = {},
        bitrateError = "INVALID MAX SIZE",
        lockOrientation = Device.Context.LockOrientation.NONE,
        lockOrientations = Device.Context.LockOrientation.values().toList(),
        onUpdateLockOrientation = {}
    )
}

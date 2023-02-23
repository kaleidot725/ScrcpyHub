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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.entity.Device
import view.parts.DropDownButton
import view.parts.TextFieldAndError
import view.resource.Strings

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

                    DropDownButton(
                        label = "Orientation",
                        selectedItem = lockOrientation.title,
                        items = lockOrientations.map { it.title },
                        onSelect = { title ->
                            val item = lockOrientations.firstOrNull { it.title == title } ?: return@DropDownButton
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

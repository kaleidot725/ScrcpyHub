package view.pages.device

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
import view.parts.DropDownSelector
import view.parts.TextFieldAndError
import view.parts.TitleAndCheckButton
import view.resource.Strings
import view.resource.Strings.DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_BORDERLESS_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_BORDERLESS_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_BUFFERING_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_BUFFERING_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_FULLSCREEN_DETAILS
import view.resource.Strings.DEVICE_PAGE_EDIT_FULLSCREEN_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_180
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_COUNTER_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_NATURAL
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_NONE
import view.resource.Strings.DEVICE_PAGE_EDIT_ORIENTATION_TITLE
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_180
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_COUNTER_CLOCK_WISE_90
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_NONE
import view.resource.Strings.DEVICE_PAGE_EDIT_ROTATION_TITLE

@Composable
fun DeviceSetting(
    state: DevicePageState,
    action: DevicePageAction,
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
                        inputText = state.editName,
                        onUpdateInputText = { action.updateName(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_STAY_AWAKE_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_STAY_AWAKE_DETAILS,
                        value = state.enableStayAwake,
                        onSelect = { action.updateEnableStayAwake(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_SHOW_TOUCHES_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_SHOW_TOUCHES_DETAILS,
                        value = state.enableShowTouches,
                        onSelect = { action.updateEnableShowTouches(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_POWER_OFF_ON_CLOSE_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_POWER_OFF_ON_CLOSE_DETAILS,
                        value = state.enablePowerOffOnClose,
                        onSelect = { action.updateEnablePowerOffOnClose(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_POWER_ON_ON_START_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_POWER_ON_ON_START_DETAILS,
                        value = state.disablePowerOnOnStart,
                        onSelect = { action.updateDisablePowerOnOnStart(it) },
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
                        inputText = state.maxSize,
                        onUpdateInputText = { action.updateMaxSize(it) },
                        error = state.maxSizeError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_DETAILS,
                        inputText = state.maxFrameRate,
                        onUpdateInputText = { action.updateMaxFrameRate(it) },
                        error = state.maxFrameRateError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_MAX_BITRATE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_MAX_BITRATE_DETAILS,
                        inputText = state.bitrate,
                        onUpdateInputText = { action.updateBitrate(it) },
                        error = state.bitrateError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = DEVICE_PAGE_EDIT_BUFFERING_TITLE,
                        placeHolder = DEVICE_PAGE_EDIT_BUFFERING_DETAILS,
                        inputText = state.buffering,
                        onUpdateInputText = { action.updateBuffering(it) },
                        error = state.bufferingError,
                        modifier = Modifier.padding(8.dp)
                    )

                    DropDownSelector(
                        label = DEVICE_PAGE_EDIT_ORIENTATION_TITLE,
                        selectedItem = state.lockOrientation.toTitle(),
                        items = state.lockOrientations.map { it.toTitle() },
                        onSelect = { title ->
                            val item = state.lockOrientations.firstOrNull {
                                it.toTitle() == title
                            } ?: return@DropDownSelector
                            action.updateLockOrientation(item)
                        },
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                    )
                }
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.DEVICE_PAGE_EDIT_AUDIO_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_AUDIO_NO_AUDIO_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_AUDIO_NO_AUDIO_DETAILS,
                        value = state.noAudio,
                        onSelect = { action.updateNoAudio(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_AUDIO_BITRATE_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_AUDIO_BITRATE_DETAILS,
                        inputText = state.audioBitrate,
                        onUpdateInputText = { action.updateAudioBitrate(it) },
                        error = state.audioBitrateError,
                        modifier = Modifier.padding(8.dp)
                    )

                    TextFieldAndError(
                        label = Strings.DEVICE_PAGE_EDIT_AUDIO_BUFFERING_TITLE,
                        placeHolder = Strings.DEVICE_PAGE_EDIT_AUDIO_BUFFERING_DETAILS,
                        inputText = state.audioBuffering,
                        onUpdateInputText = { action.updateAudioBuffering(it) },
                        error = state.audioBufferingError,
                        modifier = Modifier.padding(8.dp)
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

                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_BORDERLESS_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_BORDERLESS_DETAILS,
                        value = state.enableBorderless,
                        onSelect = { action.updateBorderless(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_DETAILS,
                        value = state.enableAlwaysOnTop,
                        onSelect = { action.updateAlwaysOnTop(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = DEVICE_PAGE_EDIT_FULLSCREEN_TITLE,
                        subTitle = DEVICE_PAGE_EDIT_FULLSCREEN_DETAILS,
                        value = state.enableFullScreen,
                        onSelect = { action.updateFullscreen(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    DropDownSelector(
                        label = DEVICE_PAGE_EDIT_ROTATION_TITLE,
                        selectedItem = state.rotation.toTitle(),
                        items = state.rotations.map { it.toTitle() },
                        onSelect = { title ->
                            val item = state.rotations.firstOrNull {
                                it.toTitle() == title
                            } ?: return@DropDownSelector
                            action.updateRotation(item)
                        },
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                    )
                }
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.DEVICE_PAGE_EDIT_HID_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_HID_KEYBOARD_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_HID_KEYBOARD_DETAILS,
                        value = state.enableHidKeyboard,
                        onSelect = { action.updateEnableHidKeyboard(it) },
                        modifier = Modifier.padding(8.dp)
                    )

                    TitleAndCheckButton(
                        title = Strings.DEVICE_PAGE_EDIT_HID_MOUSE_TITLE,
                        subTitle = Strings.DEVICE_PAGE_EDIT_HID_MOUSE_DETAILS,
                        value = state.enableHidMouse,
                        onSelect = { action.updateEnableHidMouse(it) },
                        modifier = Modifier.padding(8.dp)
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

private fun Device.Context.Rotation.toTitle(): String {
    return when (this) {
        Device.Context.Rotation.NONE -> DEVICE_PAGE_EDIT_ROTATION_NONE
        Device.Context.Rotation.COUNTER_CLOCK_WISE_90 -> DEVICE_PAGE_EDIT_ROTATION_COUNTER_CLOCK_WISE_90
        Device.Context.Rotation.CLOCK_WISE_180 -> DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_180
        Device.Context.Rotation.CLOCK_WISE_90 -> DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_90
    }
}

@Preview
@Composable
private fun DeviceSetting_Savable_True_Preview() {
    DeviceSetting(
        state = DevicePageState(savable = true),
        action = object : DevicePageAction {
            override fun updateName(name: String) {}
            override fun updateEnableStayAwake(enable: Boolean) {}
            override fun updateEnableShowTouches(enable: Boolean) {}
            override fun updateEnablePowerOffOnClose(enable: Boolean) {}
            override fun updateDisablePowerOnOnStart(disable: Boolean) {}
            override fun updateMaxSize(maxSize: String) {}
            override fun updateMaxFrameRate(maxFrameRate: String) {}
            override fun updateBitrate(bitrate: String) {}
            override fun updateBuffering(buffering: String) {}
            override fun updateNoAudio(noAudio: Boolean) {}
            override fun updateAudioBitrate(bitrate: String) {}
            override fun updateAudioBuffering(buffering: String) {}
            override fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation) {}
            override fun updateBorderless(enabled: Boolean) {}
            override fun updateAlwaysOnTop(enabled: Boolean) {}
            override fun updateFullscreen(enabled: Boolean) {}
            override fun updateRotation(rotation: Device.Context.Rotation) {}
            override fun updateEnableHidKeyboard(enabled: Boolean) {}
            override fun updateEnableHidMouse(enabled: Boolean) {}
            override fun save() {}
        },
    )
}

@Preview
@Composable
private fun DeviceSetting_Savable_False_Preview() {
    DeviceSetting(
        state = DevicePageState(savable = false),
        action = object : DevicePageAction {
            override fun updateName(name: String) {}
            override fun updateEnableStayAwake(enable: Boolean) {}
            override fun updateEnableShowTouches(enable: Boolean) {}
            override fun updateEnablePowerOffOnClose(enable: Boolean) {}
            override fun updateDisablePowerOnOnStart(disable: Boolean) {}
            override fun updateMaxSize(maxSize: String) {}
            override fun updateMaxFrameRate(maxFrameRate: String) {}
            override fun updateBitrate(bitrate: String) {}
            override fun updateBuffering(buffering: String) {}
            override fun updateNoAudio(noAudio: Boolean) {}
            override fun updateAudioBitrate(bitrate: String) {}
            override fun updateAudioBuffering(buffering: String) {}
            override fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation) {}
            override fun updateBorderless(enabled: Boolean) {}
            override fun updateAlwaysOnTop(enabled: Boolean) {}
            override fun updateFullscreen(enabled: Boolean) {}
            override fun updateRotation(rotation: Device.Context.Rotation) {}
            override fun updateEnableHidKeyboard(enabled: Boolean) {}
            override fun updateEnableHidMouse(enabled: Boolean) {}
            override fun save() {}
        },
    )
}

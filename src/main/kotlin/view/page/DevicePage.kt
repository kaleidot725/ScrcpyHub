package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import resource.Images
import resource.Strings
import view.components.SaveButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.DevicePageViewModel

@Composable
fun DevicePage(
    windowScope: WindowScope,
    deviceViewModel: DevicePageViewModel,
    onNavigateDevices: (() -> Unit)? = null
) {
    onInitialize(deviceViewModel)
    onDrawPage(windowScope, deviceViewModel, onNavigateDevices)
}

@Composable
private fun onDrawPage(
    windowScope: WindowScope,
    viewModel: DevicePageViewModel,
    onNavigateDevices: (() -> Unit)? = null
) {
    val titleName: String by viewModel.titleName.collectAsState()
    val name: String by viewModel.editName.collectAsState()
    val maxSize: String by viewModel.maxSize.collectAsState()
    val maxSizeError: String by viewModel.maxSizeError.collectAsState()
    val savable: Boolean by viewModel.savable.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            windowScope = windowScope,
            title = titleName,
            optionContent = {
                Image(
                    painter = painterResource(Images.CLOSE),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(18.dp)
                        .clickable { onNavigateDevices?.invoke() }
                )
            }
        )

        DeviceNameSetting(
            name,
            { viewModel.updateName(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        MaxSizeSetting(
            maxSize,
            maxSizeError,
            { viewModel.updateMaxSize(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        SaveButton(
            savable = savable,
            modifier = Modifier.padding(horizontal = 8.dp),
            onSaved = { viewModel.save() }
        )
    }
}

@Composable
private fun DeviceNameSetting(deviceName: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                Strings.DEVICE_PAGE_EDIT_NAME_TITLE,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                Strings.DEVICE_PAGE_EDIT_NAME_DETAILS,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = deviceName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) },
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun DeviceNameSetting_Preview() {
    DeviceNameSetting("DEVICE", {})
}

@Composable
private fun MaxSizeSetting(maxSize: String, error: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    val hasError = error.isNotEmpty()

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                Strings.DEVICE_PAGE_EDIT_MAX_SIZE_TITLE,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                Strings.DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = maxSize,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { onUpdate(it) },
                isError = hasError,
                maxLines = 1
            )

            if (hasError) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    error,
                    style = MaterialTheme.typography.caption,
                    color = Color.Red,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MaxSizeSetting_Preview() {
    MaxSizeSetting("1920", "", {})
}

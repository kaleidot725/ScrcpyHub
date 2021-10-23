package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Images
import resource.Strings
import view.components.SaveButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.DevicePageViewModel

@Composable
fun DevicePage(
    deviceViewModel: DevicePageViewModel,
    onNavigateDevices: (() -> Unit)? = null
) {
    onInitialize(deviceViewModel)
    onDrawPage(deviceViewModel, onNavigateDevices)
}

@Composable
private fun onDrawPage(viewModel: DevicePageViewModel, onNavigateDevices: (() -> Unit)? = null) {
    val titleName: String by viewModel.titleName.collectAsState()
    val name: String by viewModel.editName.collectAsState()
    val maxSize: String by viewModel.maxSize.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            title = titleName,
            icon = painterResource(Images.CLOSE),
            onAction = { onNavigateDevices?.invoke() }
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
            { viewModel.updateMaxSize(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        SaveButton(modifier = Modifier.padding(horizontal = 8.dp), onSaved = { viewModel.save() })
    }
}

@Composable
private fun DeviceNameSetting(deviceName: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(Strings.DEVICE_PAGE_EDIT_NAME_TITLE, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                Strings.DEVICE_PAGE_EDIT_NAME_DETAILS,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = deviceName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) }
            )
        }
    }
}

@Composable
private fun MaxSizeSetting(maxSize: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(Strings.DEVICE_PAGE_EDIT_MAX_SIZE_TITLE, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                Strings.DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = maxSize,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { onUpdate(it) }
            )
        }
    }
}

@Preview
@Composable
private fun DeviceNameSetting_Preview() {
    DeviceNameSetting("DEVICE", {})
}

@Preview
@Composable
private fun MaxSizeSetting_Preview() {
    MaxSizeSetting("1920", {})
}

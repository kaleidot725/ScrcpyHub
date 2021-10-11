package view.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Images
import resource.Strings
import resource.Strings.ADB_LOCATION
import resource.Strings.IF_ADB_LOCATION_IS_EMPTY
import resource.Strings.IF_SCRCPY_LOCATION_IS_EMPTY
import resource.Strings.SAVE
import resource.Strings.SCRCPY_LOCATION
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.SettingPageViewModel

@Composable
fun SettingPage(
    settingPageViewModel: SettingPageViewModel,
    onNavigateDevice: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    onInitialize(settingPageViewModel)
    onDrawPage(settingPageViewModel, onNavigateDevice, onSaved)
}

@Composable
private fun onDrawPage(
    viewModel: SettingPageViewModel,
    onNavigateDevice: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    val adbLocation: String by viewModel.adbLocation.collectAsState()
    val scrcpyLocation: String by viewModel.scrcpyLocation.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            title = Strings.SETTING_PAGE_TITLE,
            icon = painterResource(Images.CLOSE),
            onAction = { onNavigateDevice?.invoke() }
        )

        Card(modifier = Modifier.padding(horizontal = 8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(ADB_LOCATION, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(IF_ADB_LOCATION_IS_EMPTY, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = adbLocation,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        viewModel.updateAdbLocation(it)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(modifier = Modifier.padding(horizontal = 8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(SCRCPY_LOCATION, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    IF_SCRCPY_LOCATION_IS_EMPTY,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = scrcpyLocation,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        viewModel.updateScrcpyLocation(it)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            Button(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                onClick = { viewModel.save() { onSaved?.invoke() } }
            ) {
                Text(SAVE)
            }
        }
    }
}
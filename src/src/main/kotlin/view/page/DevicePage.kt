package view.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import resource.Images
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
    Box(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            title = "Device Name",
            icon = painterResource(Images.CLOSE),
            onAction = { onNavigateDevices?.invoke() }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                "SAMPLE SAMPLE SAMPLE",
                style = TextStyle(color = Color.Black, fontSize = 18.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

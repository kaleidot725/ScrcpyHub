package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import resource.Images
import resource.Strings
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.DevicesPageViewModel

@Composable
fun DevicesPage(
    devicesPageViewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null
) {
    onInitialize(devicesPageViewModel)
    onDrawPage(devicesPageViewModel, onNavigateSetting)
}

@Composable
private fun onDrawPage(viewModel: DevicesPageViewModel, onNavigateSetting: (() -> Unit)? = null) {
    val states: List<Pair<Device, Boolean>> by viewModel.states.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (states.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                PageHeader(
                    title = Strings.APP_NAME,
                    icon = painterResource(Images.SETTING),
                    onAction = { onNavigateSetting?.invoke() }
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        Strings.NO_ANDROID_DEVICE,
                        style = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    PageHeader(
                        title = Strings.APP_NAME,
                        icon = painterResource(Images.SETTING),
                        onAction = { onNavigateSetting?.invoke() }
                    )
                }
                items(states, itemContent = { device ->
                    DeviceCard(
                        device = device.first,
                        isRunning = device.second,
                        startScrcpy = { viewModel.startScrcpy(it) },
                        stopScrcpy = { viewModel.stopScrcpy(it) },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                })
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: Device,
    isRunning: Boolean,
    startScrcpy: ((Device) -> Unit)? = null,
    stopScrcpy: ((Device) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 8.dp).height(48.dp)) {
            Image(
                painter = painterResource(Images.DEVICE_BLACK),
                contentDescription = Images.DEVICE_BLACK,
                contentScale = ContentScale.Inside,
                modifier = Modifier.width(32.dp).align(Alignment.CenterVertically).padding(end = 4.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.7f).align(Alignment.CenterVertically)
            ) {
                Text(
                    device.name,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                )

                Text(
                    device.id,
                    style = TextStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                )
            }

            Button(
                onClick = { if (!isRunning) startScrcpy?.invoke(device) else stopScrcpy?.invoke(device) },
                modifier = Modifier.width(80.dp).height(30.dp).align(Alignment.CenterVertically)
            ) {
                Text(if (!isRunning) Strings.RUN else Strings.STOP, fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview() {
    DeviceCard(Device("ID", "NAME"), false)
}
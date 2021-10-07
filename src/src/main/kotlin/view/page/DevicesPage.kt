package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import resource.Colors
import resource.Strings
import view.extention.onInitialize
import view.tab.DevicesHeader
import viewmodel.DevicesPageViewModel

@Composable
fun DevicesPage(
    devicesPageViewModel: DevicesPageViewModel = DevicesPageViewModel(),
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
                DevicesHeader(onNavigateSetting = { onNavigateSetting?.invoke() })
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        Strings.NO_ANDROID_DEVICE,
                        style = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { DevicesHeader(onNavigateSetting = { onNavigateSetting?.invoke() }) }
                items(states, itemContent = { device -> DeviceCard(device.first, device.second, viewModel) })
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: Device,
    isRunning: Boolean,
    viewModel: DevicesPageViewModel
) {
    Card(
        modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                if (!isRunning) Strings.RUN else Strings.STOP,
                style = TextStyle(color = Colors.NAVY, fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd).padding(8.dp).clickable {
                    if (!isRunning) viewModel.startScrcpy(device) else viewModel.stopScrcpy(device)
                }
            )

            Text(
                device.id,
                style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.wrapContentSize().align(Alignment.CenterStart)
            )
        }
    }
}


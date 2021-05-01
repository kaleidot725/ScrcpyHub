package view

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import resource.Strings
import view.extention.onCreated
import view.extention.onDestroyed
import viewmodel.DevicesPageViewModel

@Composable
fun DevicesPage(devicesPageViewModel: DevicesPageViewModel = DevicesPageViewModel()) {
    onCreated(devicesPageViewModel)
    onDrawPage(devicesPageViewModel)
    onDestroyed(devicesPageViewModel)
}

@Composable
private fun onDrawPage(viewModel: DevicesPageViewModel) {
    val states: List<Pair<Device, Boolean>> by viewModel.states.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (states.isEmpty()) {
            Text(
                Strings.NO_ANDROID_DEVICE,
                style = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(states, itemContent = { device -> DeviceCard(device.first, device.second, viewModel) })
            }
        }

//        FloatingActionButton(
//            onClick = { viewModel.refresh() },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .width(64.dp)
//                .height(64.dp)
//                .padding(bottom = 16.dp, end = 16.dp)
//        ) {
//            Image(
//                imageFromResource(Images.RESTART_WHITE),
//                Images.RESTART_WHITE,
//                contentScale = ContentScale.FillHeight,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
    }
}

@Composable
private fun DeviceCard(
    device: Device,
    isRunning: Boolean,
    viewModel: DevicesPageViewModel
) {
    Card(
        modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = { if (!isRunning) viewModel.startScrcpy(device) else viewModel.stopScrcpy(device) },
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd).width(80.dp)
            ) {
                Text(
                    if (!isRunning) Strings.RUN else Strings.STOP,
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                )
            }

            Text(
                device.id,
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier.wrapContentSize().align(Alignment.CenterStart)
            )
        }
    }
}


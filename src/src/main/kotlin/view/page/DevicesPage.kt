package view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import resource.Images
import resource.Strings
import view.components.DeviceCard
import view.components.RefreshButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.DevicesPageViewModel

@Composable
fun DevicesPage(
    devicesPageViewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device) -> Unit)? = null
) {
    onInitialize(devicesPageViewModel)
    onDrawPage(devicesPageViewModel, onNavigateSetting, onNavigateDevice)
}

@Composable
private fun onDrawPage(
    viewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device) -> Unit)? = null
) {
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
                        Strings.DEVICES_PAGE_NOT_FOUND_DEVICES,
                        style = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else {
            Column {
                PageHeader(
                    title = Strings.APP_NAME,
                    icon = painterResource(Images.SETTING),
                    onAction = { onNavigateSetting?.invoke() }
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        states,
                        itemContent = { device ->
                            DeviceCard(
                                device = device.first,
                                isRunning = device.second,
                                startScrcpy = { viewModel.startScrcpy(it) },
                                stopScrcpy = { viewModel.stopScrcpy(it) },
                                goToDetail = { onNavigateDevice?.invoke(device.first) },
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                            )
                        }
                    )
                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }

        RefreshButton(
            onReload = { viewModel.refresh() },
            modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd).padding(8.dp)
        )
    }
}
package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowScope
import model.entity.Device
import resource.Images
import resource.Strings
import resource.Strings.DEVICES_DROP_DOWN_QUIT_MENU_TITLE
import resource.Strings.DEVICES_DROP_DOWN_SETTING_MENU_TITLE
import view.components.DeviceCard
import view.components.RefreshButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.DevicesPageViewModel
import kotlin.system.exitProcess

@Composable
fun DevicesPage(
    windowScope: WindowScope,
    devicesPageViewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device) -> Unit)? = null
) {
    onInitialize(devicesPageViewModel)
    onDrawPage(windowScope, devicesPageViewModel, onNavigateSetting, onNavigateDevice)
}

@Composable
private fun onDrawPage(
    windowScope: WindowScope,
    viewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device) -> Unit)? = null
) {
    val states: List<Pair<Device, Boolean>> by viewModel.states.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (states.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                DevicePageHeader(windowScope = windowScope, onNavigateSetting)

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
                DevicePageHeader(windowScope = windowScope, onNavigateSetting)

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(states, itemContent = { device ->
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

@Composable
private fun DevicePageHeader(windowScope: WindowScope, onNavigateSetting: (() -> Unit)?) {
    PageHeader(
        windowScope = windowScope,
        title = Strings.APP_NAME,
        optionContent = {
            ApplicationDropDownMenu(
                onSetting = { onNavigateSetting?.invoke() },
                onQuit = { exitProcess(0) },
                modifier = Modifier.height(18.dp).width(24.dp)
            )
        }
    )
}

@Composable
private fun ApplicationDropDownMenu(
    onSetting: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(Images.SETTING),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.matchParentSize().clickable { expanded = true }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                onSetting()
            }) {
                Text(text = DEVICES_DROP_DOWN_SETTING_MENU_TITLE)
            }

            DropdownMenuItem(onClick = {
                onQuit()
            }) {
                Text(text = DEVICES_DROP_DOWN_QUIT_MENU_TITLE)
            }
        }
    }

}
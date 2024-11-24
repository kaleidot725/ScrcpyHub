package view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Device
import view.pages.devices.DeviceStatus

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceList(
    deviceStatusList: List<DeviceStatus>,
    startScrcpy: ((Device.Context) -> Unit),
    stopScrcpy: ((Device.Context) -> Unit),
    goToDetail: ((Device.Context) -> Unit),
    takeScreenshot: ((Device.Context) -> Unit),
    startRecording: ((Device.Context) -> Unit),
    stopRecording: ((Device.Context) -> Unit),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val lazyColumnState = rememberLazyListState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyColumnState,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp),
        ) {
            items(items = deviceStatusList, key = { it.context.device.id }) { deviceStatus ->
                DeviceCard(
                    deviceStatus,
                    true,
                    startScrcpy,
                    stopScrcpy,
                    goToDetail,
                    takeScreenshot,
                    startRecording,
                    stopRecording,
                    Modifier.animateItemPlacement(),
                )
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(lazyColumnState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        )
    }
}

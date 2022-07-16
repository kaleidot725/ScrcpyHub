package view.components.organisms

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Device
import view.pages.DeviceStatus

@Composable
fun DeviceList(
    deviceStatusList: List<DeviceStatus>,
    startScrcpy: ((Device.Context) -> Unit)? = null,
    stopScrcpy: ((Device.Context) -> Unit)? = null,
    goToDetail: ((Device.Context) -> Unit)? = null,
    takeScreenshot: ((Device.Context) -> Unit)? = null,
    startRecording: ((Device.Context) -> Unit)? = null,
    stopRecording: ((Device.Context) -> Unit)? = null,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {
            deviceStatusList.forEach { deviceStatus ->
                DeviceCard(
                    deviceStatus, startScrcpy, stopScrcpy, goToDetail, takeScreenshot, startRecording, stopRecording
                )
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }
}

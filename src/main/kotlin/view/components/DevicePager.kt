package view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import model.entity.Device
import view.pages.devices.DeviceStatus

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DevicePager(
    deviceStatusList: List<DeviceStatus>,
    startScrcpy: ((Device.Context) -> Unit),
    stopScrcpy: ((Device.Context) -> Unit),
    goToDetail: ((Device.Context) -> Unit),
    takeScreenshot: ((Device.Context) -> Unit),
    startRecording: ((Device.Context) -> Unit),
    stopRecording: ((Device.Context) -> Unit),
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pageCount = deviceStatusList.count()
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        HorizontalPager(
            pageCount = pageCount,
            state = pagerState
        ) { page ->
            DeviceCard(
                deviceStatusList[page],
                false,
                startScrcpy,
                stopScrcpy,
                goToDetail,
                takeScreenshot,
                startRecording,
                stopRecording,
                modifier = Modifier.wrapContentSize().padding(8.dp)
            )
        }

        Row(
            Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                        .clickable { coroutineScope.launch { pagerState.animateScrollToPage(iteration) } }
                )
            }
        }
    }
}

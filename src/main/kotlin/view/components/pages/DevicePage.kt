package view.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import resource.Images
import view.components.organisms.DeviceSetting
import view.components.templates.DeviceTemplate
import view.tab.PageHeader
import viewmodel.DevicePageViewModel

@Composable
fun DevicePage(
    windowScope: WindowScope,
    deviceViewModel: DevicePageViewModel,
    onNavigateDevices: (() -> Unit)? = null
) {
    val titleName: String by deviceViewModel.titleName.collectAsState()
    val name: String by deviceViewModel.editName.collectAsState()
    val maxSize: String by deviceViewModel.maxSize.collectAsState()
    val maxSizeError: String by deviceViewModel.maxSizeError.collectAsState()
    val savable: Boolean by deviceViewModel.savable.collectAsState()

    DeviceTemplate(header = {
        PageHeader(windowScope = windowScope, title = titleName, optionContent = {
            Image(painter = painterResource(Images.CLOSE),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.wrapContentWidth().height(18.dp).clickable { onNavigateDevices?.invoke() })
        })
    }, content = {
        DeviceSetting(name = name,
            onUpdateName = { deviceViewModel.updateName(it) },
            maxSize = maxSize,
            onUpdateMaxSize = { deviceViewModel.updateMaxSize(it) },
            maxSizeError = maxSizeError,
            savable = savable,
            onSave = { deviceViewModel.save() })
    })
}

import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
import model.usecase.*
import resource.Navigation
import resource.Strings
import view.DeviceListView
import view.ResolutionListView
import view.RunAndStopButton

fun main() = Window(
    size = IntSize(825, 580),
    title = Strings.APP_NAME
) {
    val adbCommand = AdbCommand()
    val scrcpyCommand = ScrcpyCommand()
    val resolutionRepository = ResolutionRepository()
    val deviceRepository = DeviceRepository(adbCommand)
    val processRepository = ProcessRepository()

    val startScrcpyUseCase = StartScrcpyUseCase(scrcpyCommand, processRepository)
    val stopScrcpyUseCase = StopScrcpyUseCase(processRepository)
    val selectDeviceUseCase = SelectDeviceUseCase(deviceRepository)
    val selectResolutionRepository = SelectResolutionUseCase(resolutionRepository)
    val fetchDeviceUseCase = FetchDevicesUseCase(deviceRepository)
    val fetchResolutionsUseCase = FetchResolutionsUseCase(resolutionRepository)

    MaterialTheme {
        var selectedPageName by remember { mutableStateOf(Navigation.CONNECT_PAGE) }

        fun getTextColor(current: String, selected: String): Color {
            return if (selected == current) Color.Blue else Color.Black
        }

        Row {
            Column(modifier = Modifier.width(150.dp).padding(4.dp)) {
                Navigation.PAGE_NAMES.forEach { pageName ->
                    Text(
                        pageName,
                        Modifier.fillMaxWidth().padding(4.dp).clickable(true) { selectedPageName = pageName },
                        color = getTextColor(pageName, selectedPageName)
                    )
                }
            }

            Column(modifier = Modifier.padding(4.dp)) {
                when (selectedPageName) {
                    Navigation.CONNECT_PAGE -> {
                        DeviceListView(fetchDeviceUseCase, selectDeviceUseCase)
                        ResolutionListView(fetchResolutionsUseCase, selectResolutionRepository)
                        RunAndStopButton(deviceRepository, resolutionRepository, startScrcpyUseCase, stopScrcpyUseCase)
                    }
                    Navigation.SETTING_PAGE -> {
                        Text("SETTING PAGE")
                    }
                }
            }
        }
    }
}
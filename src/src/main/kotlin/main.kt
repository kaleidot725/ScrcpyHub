import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import view.ConnectPage
import view.page.SettingPage

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
    val selectResolutionUseCase = SelectResolutionUseCase(resolutionRepository)
    val fetchDevicesUseCase = FetchDevicesUseCase(deviceRepository)
    val fetchResolutionsUseCase = FetchResolutionsUseCase(resolutionRepository)

    MaterialTheme {
        var selectedPageName by remember { mutableStateOf(Navigation.CONNECT_PAGE) }

        Row {
            Column(modifier = Modifier.width(150.dp).padding(4.dp)) {
                Navigation.PAGE_NAMES.forEach { pageName ->
                    Text(
                        pageName,
                        Modifier.fillMaxWidth().padding(4.dp).clickable(true) { selectedPageName = pageName },
                        color = if (selectedPageName == pageName) Color.Blue else Color.Black
                    )
                }
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Crossfade(selectedPageName, animation = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.CONNECT_PAGE -> {
                            ConnectPage(
                                fetchDevicesUseCase,
                                selectDeviceUseCase,
                                fetchResolutionsUseCase,
                                selectResolutionUseCase,
                                deviceRepository,
                                resolutionRepository,
                                startScrcpyUseCase,
                                stopScrcpyUseCase
                            )
                        }
                        Navigation.SETTING_PAGE -> {
                            SettingPage()
                        }
                    }
                }
            }
        }
    }
}
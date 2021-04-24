import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
import model.usecase.FetchDevicesUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import resource.Colors
import resource.Navigation
import resource.Strings
import view.ConnectPage
import view.page.MenuPage
import view.page.SettingPage

fun main() = Window(
    resizable = false,
    size = IntSize(400, 580),
    title = Strings.APP_NAME
) {
    val adbCommand = AdbCommand()
    val scrcpyCommand = ScrcpyCommand()
    val resolutionRepository = ResolutionRepository()
    val deviceRepository = DeviceRepository(adbCommand)
    val processRepository = ProcessRepository()

    val startScrcpyUseCase = StartScrcpyUseCase(scrcpyCommand, processRepository)
    val stopScrcpyUseCase = StopScrcpyUseCase(processRepository)
    val fetchDevicesUseCase = FetchDevicesUseCase(deviceRepository)

    MaterialTheme {
        var selectedPageName by remember { mutableStateOf(Navigation.DEFAULT_PAGE) }

        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            Column(modifier = Modifier.padding(8.dp)) {
                MenuPage(
                    menuNames = Navigation.PAGE_NAMES,
                    selectedMenuName = selectedPageName,
                    onSelected = { selectedPageName = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Crossfade(selectedPageName, animationSpec = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.DEVICES_PAGE -> {
                            ConnectPage(fetchDevicesUseCase, startScrcpyUseCase, stopScrcpyUseCase)
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

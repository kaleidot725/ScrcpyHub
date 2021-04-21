import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
import model.usecase.*
import resource.Navigation
import resource.Strings
import view.ConnectPage
import view.page.MenuPage
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
            MenuPage(
                menuNames = Navigation.PAGE_NAMES,
                selectedMenuName = selectedPageName,
                onSelected = { selectedPageName = it }
            )

            Crossfade(selectedPageName, animation = tween(100)) { selectedPageName ->
                when (selectedPageName) {
                    Navigation.CONNECT_PAGE -> {
                        ConnectPage(
                            fetchDevicesUseCase, selectDeviceUseCase, fetchResolutionsUseCase,
                            selectResolutionUseCase, deviceRepository, resolutionRepository,
                            startScrcpyUseCase, stopScrcpyUseCase
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
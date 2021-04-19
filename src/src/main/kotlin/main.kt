import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
import model.usecase.*
import resource.Strings
import view.DeviceListView
import view.ResolutionListView
import view.RunAndStopButton

fun main() = Window(
    size = IntSize(250, 300),
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
        Column(modifier = Modifier.padding(8.dp)) {
            DeviceListView(fetchDeviceUseCase, selectDeviceUseCase)
            ResolutionListView(fetchResolutionsUseCase, selectResolutionRepository)
            RunAndStopButton(deviceRepository, resolutionRepository, startScrcpyUseCase, stopScrcpyUseCase)
        }
    }
}
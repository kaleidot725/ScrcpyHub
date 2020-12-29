import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import model.repository.DeviceRepository
import model.repository.ResolutionRepository
import view.DeviceListView
import view.ResolutionListView

fun main() = Window {
    val resolutionRepository = ResolutionRepository()
    val deviceRepository = DeviceRepository()

    MaterialTheme {
        Column {
            ResolutionListView(resolutionRepository)
            DeviceListView(deviceRepository)
        }
    }
}
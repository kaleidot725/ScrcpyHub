import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.repository.DeviceRepository
import model.repository.ResolutionRepository
import view.DeviceListView
import view.ResolutionListView

fun main() = Window(
    size = IntSize(250, 600)
) {
    val resolutionRepository = ResolutionRepository()
    val deviceRepository = DeviceRepository()

    MaterialTheme {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            DeviceListView(deviceRepository)
            ResolutionListView(resolutionRepository)
        }
    }
}
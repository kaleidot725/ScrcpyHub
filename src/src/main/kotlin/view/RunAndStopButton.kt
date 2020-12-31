package view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.repository.DeviceRepository
import resource.Strings

@Composable
fun RunAndStopButton(
    deviceRepository: DeviceRepository
) {
    var running by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (running) {
                val success = deviceRepository.stop()
                if (success) running = false
            } else {
                val success = deviceRepository.run()
                if (success) running = true
            }
        },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Text(if (running) Strings.STOP else Strings.RUN)
    }
}
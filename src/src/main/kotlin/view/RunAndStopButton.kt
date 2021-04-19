package view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
import resource.Strings

@Composable
fun RunAndStopButton(
    deviceRepository: DeviceRepository,
    resolutionRepository: ResolutionRepository,
    processRepository: ProcessRepository
) {
    var running by remember { mutableStateOf(false) }

    Button(
        onClick = {
//            if (running) {
//                processRepository.delete()
//            } else {
//                val srcpyCommand = ScrcpyCommand(deviceRepository.selected, resolutionRepository.selected)
//                val success = processRepository.run(srcpyCommand) { running = false }
//                if (success) running = true
//            }
        },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Text(if (running) Strings.STOP else Strings.RUN)
    }
}
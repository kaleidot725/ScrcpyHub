package view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.command.ScrcpyCommand
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
            val targetDevice = deviceRepository.selected ?: return@Button
            val targetResolution = resolutionRepository.selected ?: return@Button

            if (running) {
                processRepository.delete(targetDevice.id)
                running = false
            } else {
                val process = ScrcpyCommand().run(targetDevice, targetResolution)
                if (process != null) {
                    processRepository.insert(targetDevice.id, process) { running = false }
                    running = true
                }
            }
        },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Text(if (running) Strings.STOP else Strings.RUN)
    }
}
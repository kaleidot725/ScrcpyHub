package view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.repository.DeviceRepository
import model.repository.ResolutionRepository
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import resource.Strings

@Composable
fun RunAndStopButton(
    deviceRepository: DeviceRepository,
    resolutionRepository: ResolutionRepository,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase
) {
    var running by remember { mutableStateOf(false) }

    Button(
        onClick = {
            val targetDevice = deviceRepository.selected ?: return@Button
            val targetResolution = resolutionRepository.selected ?: return@Button

            if (running) {
                stopScrcpyUseCase.execute(targetDevice)
                running = false
            } else {
                startScrcpyUseCase.execute(targetDevice, targetResolution) { running = false }
                running = true
            }
        },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Text(if (running) Strings.STOP else Strings.RUN)
    }
}
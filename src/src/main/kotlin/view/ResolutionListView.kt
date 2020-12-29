package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.Resolution
import model.repository.ResolutionRepository

@Composable
fun ResolutionListView(resolutionRepository: ResolutionRepository) {
    val resoutions = resolutionRepository.getAll()
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    DropdownMenu(
        toggle = {
            Text(
                resoutions[selectedIndex].getResolutionLabel(),
                modifier = Modifier.fillMaxWidth().clickable(onClick = { showMenu = true })
            )
        },
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        toggleModifier = Modifier.fillMaxWidth().background(Color.Transparent),
        dropdownModifier = Modifier.fillMaxWidth().background(Color.White),
    ) {
        resoutions.forEachIndexed { index, resolution ->
            DropdownMenuItem(
                enabled = true,
                onClick = {
                    selectedIndex = index
                    showMenu = false
                }
            ) {
                Text(text = resolution.getResolutionLabel())
            }
        }
    }
}

private fun Resolution.getResolutionLabel(): String {
    return "$name ($width x $height)"
}
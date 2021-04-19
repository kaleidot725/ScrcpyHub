package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.entity.Resolution
import model.usecase.FetchResolutionsUseCase
import model.usecase.SelectResolutionUseCase
import resource.Strings

@Composable
fun ResolutionListView(
    fetchResolutionsUseCase: FetchResolutionsUseCase,
    selectResolutionUseCase: SelectResolutionUseCase
) {
    val resolutions = fetchResolutionsUseCase.execute()
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Text(text = Strings.RESOLUTION_TITLE, modifier = Modifier.padding(vertical = 8.dp))

    DropdownMenu(
        toggle = {
            Text(
                resolutions[selectedIndex].getResolutionLabel(),
                modifier = Modifier.fillMaxWidth().clickable(onClick = { showMenu = true })
            )
        },
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        toggleModifier = Modifier.fillMaxWidth().background(Color.Transparent).padding(bottom = 8.dp),
        dropdownModifier = Modifier.fillMaxWidth().background(Color.White),
    ) {
        resolutions.forEachIndexed { index, resolution ->
            DropdownMenuItem(
                enabled = true,
                onClick = {
                    selectedIndex = index
                    showMenu = false
                    selectResolutionUseCase.execute(resolution)
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
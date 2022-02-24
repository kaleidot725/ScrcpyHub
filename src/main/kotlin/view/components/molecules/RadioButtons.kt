package view.components.molecules

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.components.atoms.Texts

@Composable
fun RadioButtons(
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        items.forEach { item ->
            RadioButton(
                selected = (item == selectedItem),
                onClick = { onSelect(item) },
                modifier = Modifier.size(16.dp)
            )
            Texts.Caption(item, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Preview
@Composable
private fun RadioButtons_Preview() {
    RadioButtons("one", listOf("one", "two", "three"), {})
}

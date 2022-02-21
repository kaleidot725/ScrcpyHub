package view.components.molecules

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.components.atoms.Texts

@Composable
fun TitleAndRadioButtons(
    subtitle1: String,
    subtitle2: String,
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Texts.Subtitle1(subtitle1)
        Texts.Subtitle2(subtitle2)
        Spacer(modifier = Modifier.height(4.dp))
        RadioButtons(selectedItem = selectedItem, items = items, onSelect = { onSelect(it) })
    }
}

@Preview
@Composable
private fun TitleAndRadioButtons_Preview() {
    TitleAndRadioButtons(
        "CUSTOM SUBTITLE1",
        "CUSTOM SUBTITLE2",
        "one",
        listOf("one", "two", "three"),
        {},
        modifier = Modifier.wrapContentSize()
    )
}
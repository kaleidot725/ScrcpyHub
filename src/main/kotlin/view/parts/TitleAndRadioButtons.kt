package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleAndRadioButtons(
    title: String,
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Texts.Subtitle1(title)
        RadioButtons(
            selectedItem = selectedItem,
            items = items,
            onSelect = { onSelect(it) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun TitleAndRadioButtons_Preview() {
    TitleAndRadioButtons(
        "CUSTOM SUBTITLE1",
        "one",
        listOf("one", "two", "three"),
        {},
        modifier = Modifier.wrapContentSize()
    )
}

package view.components.organisms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resource.Strings
import view.components.atoms.Texts
import view.components.molecules.TitleAndTextField

@Composable
fun DeviceSetting(
    name: String,
    onUpdateName: (String) -> Unit,
    maxSize: String,
    onUpdateMaxSize: (String) -> Unit,
    maxSizeError: String,
    savable: Boolean,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TitleAndTextField(
            subtitle1 = Strings.DEVICE_PAGE_EDIT_NAME_TITLE,
            subtitle2 = Strings.DEVICE_PAGE_EDIT_NAME_DETAILS,
            inputText = name,
            onUpdateInputText = { onUpdateName(it) },
        )

        TitleAndTextField(
            subtitle1 = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_TITLE,
            subtitle2 = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS,
            inputText = maxSize,
            onUpdateInputText = { onUpdateMaxSize(it) },
            error = maxSizeError
        )

        Button(
            enabled = savable, onClick = { onSave() }, modifier = Modifier.fillMaxWidth()
        ) {
            Texts.Button(Strings.SAVE)
        }
    }
}

@Preview
@Composable
private fun DeviceSetting_Savable_True_Preview() {
    DeviceSetting(
        name = "CUSTOM NAME",
        onUpdateName = {},
        maxSize = "1200",
        onUpdateMaxSize = {},
        maxSizeError = "",
        savable = true,
        onSave = {}
    )
}

@Preview
@Composable
private fun DeviceSetting_Savable_False_Preview() {
    DeviceSetting(
        name = "CUSTOM NAME",
        onUpdateName = {},
        maxSize = "ERROR VALUE",
        onUpdateMaxSize = {},
        maxSizeError = "INVALID MAX SIZE",
        savable = false,
        onSave = {}
    )
}
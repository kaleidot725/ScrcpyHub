package view.components.organisms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
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
    maxFrameRate: String,
    onUpdateFrameRate: (String) -> Unit,
    maxFrameRateError: String,
    savable: Boolean,
    onSave: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Card(elevation = 4.dp) {
            TitleAndTextField(
                subtitle1 = Strings.DEVICE_PAGE_EDIT_NAME_TITLE,
                subtitle2 = Strings.DEVICE_PAGE_EDIT_NAME_DETAILS,
                inputText = name,
                onUpdateInputText = { onUpdateName(it) },
                modifier = Modifier.padding(8.dp)
            )
        }

        Card(elevation = 4.dp) {
            TitleAndTextField(
                subtitle1 = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_TITLE,
                subtitle2 = Strings.DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS,
                inputText = maxSize,
                onUpdateInputText = { onUpdateMaxSize(it) },
                error = maxSizeError,
                modifier = Modifier.padding(8.dp)
            )
        }

        Card(elevation = 4.dp) {
            TitleAndTextField(
                subtitle1 = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_TITLE,
                subtitle2 = Strings.DEVICE_PAGE_EDIT_MAX_FRAME_RATE_DETAILS,
                inputText = maxFrameRate,
                onUpdateInputText = { onUpdateFrameRate(it) },
                error = maxFrameRateError,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            enabled = savable,
            onClick = { onSave() },
            modifier = Modifier.fillMaxWidth()
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
        maxFrameRate = "60",
        onUpdateFrameRate = {},
        maxFrameRateError = "",
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
        maxFrameRate = "ERROR VALUE",
        onUpdateFrameRate = {},
        maxFrameRateError = "INVALID MAX FRAME RATE",
        savable = false,
        onSave = {}
    )
}

package view.components.organisms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Theme
import resource.Strings
import view.components.atoms.Texts
import view.components.molecules.TitleAndRadioButtons
import view.components.molecules.TitleAndTextField

@Composable
fun AppSetting(
    theme: Theme,
    themes: List<Theme>,
    onUpdateTheme: (Theme) -> Unit,
    adbLocation: String,
    onUpdateAdbLocation: (String) -> Unit,
    scrcpyLocation: String,
    onUpdateScrcpyLocation: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Card {
            TitleAndRadioButtons(
                subtitle1 = Strings.SETTING_PAGE_EDIT_THEME_TITLE,
                subtitle2 = Strings.SETTING_PAGE_EDIT_THEME_DETAILS,
                selectedItem = theme.toLabel(),
                items = themes.map { it.toLabel() },
                onSelect = { label -> onUpdateTheme(themes.first { it.toLabel() == label }) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
        }

        Card {
            TitleAndTextField(
                subtitle1 = Strings.SETTING_PAGE_EDIT_ADB_LOCATION_TITLE,
                subtitle2 = Strings.SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS,
                inputText = adbLocation,
                onUpdateInputText = { onUpdateAdbLocation(it) },
                modifier = Modifier.padding(8.dp)
            )
        }

        Card {
            TitleAndTextField(
                subtitle1 = Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE,
                subtitle2 = Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS,
                inputText = scrcpyLocation,
                onUpdateInputText = { onUpdateScrcpyLocation(it) },
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(onClick = { onSave() }, modifier = Modifier.fillMaxWidth()) {
            Texts.Button(Strings.SAVE)
        }
    }
}

private fun Theme.toLabel(): String {
    return when (this) {
        Theme.LIGHT -> Strings.SETTING_THEME_LIGHT
        Theme.DARK -> Strings.SETTING_THEME_DARK
        Theme.SYNC_WITH_OS -> Strings.SETTING_THEME_SYNC_WITH_OS
    }
}

private fun String.toTheme(): Theme {
    return when (this) {
        Strings.SETTING_THEME_LIGHT -> Theme.LIGHT
        Strings.SETTING_THEME_DARK -> Theme.DARK
        else -> Theme.SYNC_WITH_OS
    }
}

@Preview
@Composable
private fun AppSetting_Preview() {
    AppSetting(
        theme = Theme.LIGHT,
        themes = Theme.values().toList(),
        onUpdateTheme = {},
        adbLocation = "CUSTOM ADB LOCATION",
        onUpdateAdbLocation = {},
        scrcpyLocation = "CUSTOM SCRCPY LOCATION",
        onUpdateScrcpyLocation = {},
        onSave = {}
    )
}
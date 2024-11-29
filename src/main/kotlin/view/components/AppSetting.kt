package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import model.entity.Theme
import view.parts.TextFieldAndError
import view.parts.TitleAndRadioButtons
import view.resource.Strings

@Composable
fun AppSetting(
    theme: Theme,
    themes: List<Theme>,
    onUpdateTheme: (Theme) -> Unit,
    adbLocation: String,
    onUpdateAdbLocation: (String) -> Unit,
    scrcpyLocation: String,
    onUpdateScrcpyLocation: (String) -> Unit,
    screenshotDirectory: String,
    onUpdateScreenshotDirectory: (String) -> Unit,
    screenRecordDirectory: String,
    onUpdateScreenRecordDirectory: (String) -> Unit,
) {
    Box {
        val scrollState = rememberScrollState()
        val adbFileLauncher =
            rememberFilePickerLauncher { file ->
                file?.let { onUpdateAdbLocation(it.path ?: "") }
            }
        val scrcpyFileLauncher =
            rememberFilePickerLauncher { file ->
                file?.let { onUpdateScrcpyLocation(it.path ?: "") }
            }
        val screenshotDirectoryLauncher =
            rememberDirectoryPickerLauncher { directory ->
                directory?.let { onUpdateScreenshotDirectory(it.path ?: "") }
            }
        val screenRecordDirectoryLauncher =
            rememberDirectoryPickerLauncher { directory ->
                directory?.let { onUpdateScreenRecordDirectory(it.path ?: "") }
            }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 8.dp)
                    .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Card(elevation = 4.dp) {
                TitleAndRadioButtons(
                    title = Strings.SETTING_PAGE_EDIT_THEME_TITLE,
                    selectedItem = theme.toLabel(),
                    items = themes.map { it.toLabel() },
                    onSelect = { label -> onUpdateTheme(themes.first { it.toLabel() == label }) },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                )
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.SETTING_PAGE_EDIT_BINARY_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1,
                    )

                    TextFieldAndError(
                        label = Strings.SETTING_PAGE_EDIT_ADB_LOCATION_TITLE,
                        placeHolder = Strings.SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS,
                        inputText = adbLocation,
                        onUpdateInputText = { onUpdateAdbLocation(it) },
                        trailingIcon = Icons.Default.FilePresent,
                        onClickTrailingIcon = { adbFileLauncher.launch() },
                        modifier = Modifier.padding(8.dp),
                    )

                    TextFieldAndError(
                        label = Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE,
                        placeHolder = Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS,
                        inputText = scrcpyLocation,
                        onUpdateInputText = { onUpdateScrcpyLocation(it) },
                        trailingIcon = Icons.Default.FilePresent,
                        onClickTrailingIcon = { scrcpyFileLauncher.launch() },
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }

            Card(elevation = 4.dp) {
                Column {
                    Text(
                        text = Strings.SETTING_PAGE_EDIT_CAPTURE_AND_RECORD_TITLE,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1,
                    )

                    TextFieldAndError(
                        label = Strings.SETTING_PAGE_EDIT_SCREEN_SHOT_TITLE,
                        placeHolder = Strings.SETTING_PAGE_EDIT_SCREEN_SHOT_DETAILS,
                        inputText = screenRecordDirectory,
                        onUpdateInputText = { onUpdateScreenshotDirectory(it) },
                        trailingIcon = Icons.Default.Folder,
                        onClickTrailingIcon = { screenshotDirectoryLauncher.launch() },
                        modifier = Modifier.padding(8.dp),
                    )

                    TextFieldAndError(
                        label = Strings.SETTING_PAGE_EDIT_SCREEN_RECORD_TITLE,
                        placeHolder = Strings.SETTING_PAGE_EDIT_SCREEN_RECORD_DETAILS,
                        inputText = screenshotDirectory,
                        onUpdateInputText = { onUpdateScreenRecordDirectory(it) },
                        trailingIcon = Icons.Default.Folder,
                        onClickTrailingIcon = { screenRecordDirectoryLauncher.launch() },
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        )
    }
}

private fun Theme.toLabel(): String {
    return when (this) {
        Theme.LIGHT -> Strings.SETTING_THEME_LIGHT
        Theme.DARK -> Strings.SETTING_THEME_DARK
        Theme.SYNC_WITH_OS -> Strings.SETTING_THEME_SYNC_WITH_OS
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
        screenshotDirectory = "CUSTOM SCREENSHOT DIRECTORY",
        onUpdateScreenRecordDirectory = {},
        screenRecordDirectory = "CUSTOM SCREENRECORD DIRECTORY",
        onUpdateScreenshotDirectory = {},
    )
}

package view.pages.license

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Dialog
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import view.resource.MainTheme
import view.resource.Strings.LICENSE_TITLE

private const val FILE_NAME = "aboutlibraries.json"

@Composable
fun LicenseDialog(isDark: Boolean, onClose: () -> Unit) {
    MainTheme(isDarkMode = isDark) {
        Dialog(
            onCloseRequest = onClose,
            title = LICENSE_TITLE,
        ) {
            LibrariesContainer(
                useResource(FILE_NAME) { it.bufferedReader().readText() },
                Modifier.fillMaxSize()
            )
        }
    }
}

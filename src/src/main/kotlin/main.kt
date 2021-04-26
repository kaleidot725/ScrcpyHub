import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import resource.Colors
import resource.Navigation
import resource.Strings
import view.ConnectPage
import view.page.MenuPage
import view.page.SettingPage

fun main() = Window(
    resizable = false,
    size = IntSize(400, 580),
    title = Strings.APP_NAME
) {
    startKoin {
        printLogger()
        modules(appModule)
    }

    MaterialTheme {
        var selectedPageName by remember { mutableStateOf(Navigation.DEFAULT_PAGE) }

        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            Column(modifier = Modifier.padding(8.dp)) {
                MenuPage(
                    menuNames = Navigation.PAGE_NAMES,
                    selectedMenuName = selectedPageName,
                    onSelected = { selectedPageName = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Crossfade(selectedPageName, animationSpec = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.DEVICES_PAGE -> ConnectPage()
                        Navigation.SETTING_PAGE -> SettingPage()
                    }
                }
            }
        }

    }
}

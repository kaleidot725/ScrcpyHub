import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import resource.Colors
import resource.Navigation
import resource.Strings
import view.DevicesPage
import view.page.SettingPage
import viewmodel.DevicesPageViewModel
import viewmodel.WindowViewModel

fun main() = Window(
    resizable = false,
    size = IntSize(400, 580),
    title = Strings.APP_NAME
) {
    startKoin {
        printLogger()
        modules(appModule)
    }

    val windowViewModel = WindowViewModel()
    val devicesPageViewModel = DevicesPageViewModel()

    MaterialTheme {
        val pages: List<String> by windowViewModel.pages.collectAsState()
        val selectedPages: String by windowViewModel.selectedPages.collectAsState()

        Box(modifier = Modifier.fillMaxSize().background(Colors.SMOKE_WHITE)) {
            Column(modifier = Modifier.padding(8.dp)) {
                PageTab(pages, selectedPages, onSelect = { windowViewModel.selectPage(it) })

                Spacer(modifier = Modifier.height(8.dp))

                Crossfade(selectedPages, animationSpec = tween(100)) { selectedPageName ->
                    when (selectedPageName) {
                        Navigation.DEVICES_PAGE -> DevicesPage(devicesPageViewModel)
                        Navigation.SETTING_PAGE -> SettingPage()
                    }
                }
            }
        }

    }
}

@Composable
fun PageTab(pages: List<String>, selectedPage: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier.width(300.dp).wrapContentHeight().padding(4.dp)) {
        pages.forEach { page ->
            Text(
                page,
                Modifier.wrapContentSize().clickable(true) { onSelect(page) },
                color = if (selectedPage == page) Color.Blue else Color.Black,
                fontWeight = if (selectedPage == page) FontWeight.Bold else FontWeight.Normal,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
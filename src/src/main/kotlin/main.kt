import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import model.di.appModule
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import resource.Strings
import view.MainContent

fun main() = singleWindowApplication(
    title = Strings.APP_NAME,
    resizable = false,
    state = WindowState(width = 350.dp, height = 550.dp),
) {
    startKoin {
        printLogger()
        modules(appModule)
    }

    MainContent(mainContentViewModel = GlobalContext.get().get())
}
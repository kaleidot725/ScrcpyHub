import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import model.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import resource.Strings
import view.MainContent

fun main() = Window(resizable = false, size = IntSize(350, 550), title = Strings.APP_NAME) {
    setupModule()
    MainContent()
}

private fun setupModule() {
    startKoin {
        printLogger()
        modules(appModule)
    }
}
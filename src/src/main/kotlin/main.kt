import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import model.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import resource.Strings
import view.MainContent
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() = Window(
    resizable = false,
    size = IntSize(350, 550),
    title = Strings.APP_NAME,
    icon = getWindowIcon()
) {
    setupModule()
    MainContent()
    setupIcon()
}

private fun setupModule() {
    startKoin {
        printLogger()
        modules(appModule)
    }
}

private fun setupIcon() {
    val image = getWindowIcon()
    if (image != null) {
        AppManager.focusedWindow?.setIcon(image)
    }
}

private fun getWindowIcon(): BufferedImage? {
    return try {
        ImageIO.read(File("icon.png"))
    } catch (e: Exception) {
        null
    }
}
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
}

private fun setupModule() {
    startKoin {
        printLogger()
        modules(appModule)
    }
}

private fun getWindowIcon(): BufferedImage {
    var image: BufferedImage? = null
    try {
        image = ImageIO.read(File("icon.png"))
    } catch (e: Exception) {
        // image file does not exist
    }

    if (image == null) {
        image = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    }

    return image
}
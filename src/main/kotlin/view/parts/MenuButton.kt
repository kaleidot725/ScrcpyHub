package view.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

enum class MenuButtonStatus {
    ACTIVE,
    ENABLE,
    DISABLE
}

data class MenuButtonColors(
    val active: Color,
    val enable: Color,
    val disable: Color,
    val textColor: Color,
    val textColorOnDisable: Color
)

@Composable
fun MenuButton(
    text: String,
    style: TextStyle,
    status: MenuButtonStatus,
    colors: MenuButtonColors,
    onIdleClick: () -> Unit,
    onActiveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        if (status == MenuButtonStatus.ACTIVE) onActiveClick()
                        if (status == MenuButtonStatus.ENABLE) onIdleClick()
                    },
                    enabled = (status == MenuButtonStatus.ENABLE) || (status == MenuButtonStatus.ACTIVE)
                )
                .background(
                    when (status) {
                        MenuButtonStatus.ACTIVE -> colors.active
                        MenuButtonStatus.ENABLE -> colors.enable
                        MenuButtonStatus.DISABLE -> colors.disable
                    }
                )
        ) {
            Text(
                text = text,
                maxLines = 1,
                style = style,
                color = when (status) {
                    MenuButtonStatus.DISABLE -> colors.textColorOnDisable
                    else -> colors.textColor
                },
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

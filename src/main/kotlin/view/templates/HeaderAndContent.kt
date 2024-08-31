package view.templates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.resource.Colors

@Composable
fun MainLayout(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    snackBar: @Composable () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Colors.WINDOW_BORDER),
    ) {
        Surface {
            Box {
                Column(modifier = Modifier.fillMaxSize()) {
                    header()
                    Box(Modifier.weight(1.0f)) { content() }
                }

                Box(Modifier.align(Alignment.BottomCenter)) {
                    snackBar()
                }
            }
        }
    }
}

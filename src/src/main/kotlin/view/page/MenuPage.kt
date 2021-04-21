package view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MenuPage(menuNames: List<String>, selectedMenuName: String, onSelected: (selectedMenuName: String) -> Unit) {
    Column(modifier = Modifier.width(150.dp).padding(4.dp)) {
        menuNames.forEach { pageName ->
            Text(
                pageName,
                Modifier.fillMaxWidth().padding(4.dp).clickable(true) { onSelected(pageName) },
                color = if (selectedMenuName == pageName) Color.Blue else Color.Black
            )
        }
    }
}
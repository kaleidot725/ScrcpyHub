package view.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MenuPage(
    menuNames: List<String>,
    selectedMenuName: String,
    onSelected: (selectedMenuName: String) -> Unit
) {
    Row(modifier = Modifier.width(300.dp).wrapContentHeight().padding(4.dp)) {
        menuNames.forEach { pageName ->
            Text(
                pageName,
                Modifier.wrapContentSize().clickable(true) { onSelected(pageName) },
                color = if (selectedMenuName == pageName) Color.Blue else Color.Black,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
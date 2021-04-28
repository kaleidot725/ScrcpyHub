package view.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageTab(pages: List<String>, selectedPage: String, onSelect: (String) -> Unit) {
    Box {
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            pages.forEach { page ->
                Text(
                    page,
                    Modifier.wrapContentSize().padding(8.dp).clickable(true) { onSelect(page) },
                    color = if (selectedPage == page) resource.Colors.NAVY else Color.Black,
                    fontWeight = if (selectedPage == page) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 20.sp
                )
            }
        }
    }

}
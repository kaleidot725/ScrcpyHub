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
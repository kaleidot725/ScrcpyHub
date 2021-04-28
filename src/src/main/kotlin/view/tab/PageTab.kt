package view.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageTab(pages: List<String>, selectedPage: String, onSelect: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().wrapContentWidth()) {
        Row(modifier = Modifier.wrapContentSize().padding(horizontal = 8.dp).align(Alignment.Center)) {
            pages.forEach { page ->
                Box(modifier = Modifier
                    .width(150.dp)
                    .wrapContentHeight()
                    .clickable(true) { onSelect(page) }
                ) {
                    Text(
                        page,
                        modifier = Modifier.padding(8.dp).align(Alignment.Center),
                        color = if (selectedPage == page) resource.Colors.NAVY else Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )

                    if (selectedPage == page) {
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth()
                                .background(resource.Colors.NAVY)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }

}
package view.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Root

@Composable
fun PageTab(pages: List<Root>, selectedPage: Root, onSelect: (Root) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().wrapContentWidth()) {
        Row(modifier = Modifier.wrapContentSize().align(Alignment.Center)) {
            pages.forEach { page ->
                Box(modifier = Modifier
                    .width(150.dp)
                    .wrapContentHeight()
                    .alpha(if (selectedPage == page) 1.0f else 0.5f)
                    .clickable(true) { onSelect(page) }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp).align(Alignment.Center),
                    ) {
                        Image(
                            imageFromResource(page.iconRes),
                            page.iconRes,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.wrapContentWidth().height(24.dp).padding(end = 8.dp)
                        )

                        Text(
                            page.name,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }

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
package view.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleAndCheckButton(
    title: String,
    subTitle: String,
    value: Boolean,
    onSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f), shape = RoundedCornerShape(4.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = subTitle,
                    fontSize = 12.sp
                )
            }

            Checkbox(
                checked = value,
                onCheckedChange = { onSelect(it) },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

package view.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Strings

@Composable
fun SettingPage() {
    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text(
            Strings.NOT_FOUND_SETTING,
            style = TextStyle(color = Color.Black, fontSize = 20.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
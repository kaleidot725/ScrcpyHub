package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowScope

@Composable
fun LoadingPage(windowScope: WindowScope) {
    onDrawPage(windowScope)
}

@Composable
private fun onDrawPage(windowScope: WindowScope) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(red = 51, blue = 51, green = 51))
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

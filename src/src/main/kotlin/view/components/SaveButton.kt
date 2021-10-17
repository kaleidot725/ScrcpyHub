package view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import resource.Strings

@Composable
fun SaveButton(onSaved: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Button(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onClick = { onSaved() }
        ) {
            Text(Strings.SAVE)
        }
    }
}
package view.extention

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import viewmodel.ViewModel

@Composable
fun onInitialize(viewModel: ViewModel) {
    DisposableEffect(Unit) {
        viewModel.onStarted()
        onDispose {
            viewModel.onCleared()
        }
    }
}

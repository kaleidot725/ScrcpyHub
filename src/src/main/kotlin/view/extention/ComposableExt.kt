package view.extention

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import viewmodel.ViewModel

@Composable
fun onCreated(viewModel: ViewModel) {
    SideEffect() {
        viewModel.onStarted()
    }
}

@Composable
fun onDestroyed(viewModel: ViewModel) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onCleared()
        }
    }
}
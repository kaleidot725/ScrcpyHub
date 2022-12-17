package view

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent

abstract class StateHolder : KoinComponent {
    val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + Dispatchers.IO)

    open fun onStarted() {}

    open fun onCleared() {
        coroutineScope.cancel("coroutine Canceled")
    }
}

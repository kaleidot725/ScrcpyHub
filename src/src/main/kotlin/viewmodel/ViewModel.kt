package viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent

open abstract class ViewModel: KoinComponent {
    val coroutineScope: CoroutineScope = MainScope()

    fun init() {}

    fun onCleared() {
        coroutineScope.cancel("coroutine Canceled")
    }
}
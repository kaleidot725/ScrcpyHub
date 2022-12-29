package model.usecase

import com.jthemedetecor.OsThemeDetector
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GetSystemDarkModeFlowUseCase() {
    operator fun invoke(): Flow<Boolean> {
        return callbackFlow {
            val detector: OsThemeDetector = OsThemeDetector.getDetector()
            this.trySend(detector.isDark)

            val listener: (Boolean) -> Unit = { isDark: Boolean ->
                this.trySend(isDark)
            }
            detector.registerListener(listener)

            awaitClose {
                detector.removeListener(listener)
            }
        }
    }
}

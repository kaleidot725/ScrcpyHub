package model.entity

sealed class Message {
    sealed class Notify : Message() {
        data class SuccessToSaveScreenshot(val context: Device.Context, val fileName: String) : Notify()
        data class FailedToSaveScreenshot(val context: Device.Context) : Notify()
        data class StartRecordingMovie(val context: Device.Context) : Notify()
        data class StopRecordingMovie(val context: Device.Context) : Notify()
        data class FailedRecordingMovie(val context: Device.Context) : Notify()
    }
   
    sealed class Error : Message() {
        object NotFoundAdbBinary : Error()
        object NotFoundScrcpyBinary : Error()
    }
}

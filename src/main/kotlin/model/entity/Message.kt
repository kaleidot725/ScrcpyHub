package model.entity

sealed class Message {
    data class SuccessToSaveScreenshot(val context: Device.Context, val fileName: String) : Message()
    data class FailedToSaveScreenshot(val context: Device.Context) : Message()
    data class StartRecordingMovie(val context: Device.Context) : Message()
    data class StopRecordingMovie(val context: Device.Context) : Message()
    data class FailedRecordingMovie(val context: Device.Context) : Message()
    object EmptyMessage : Message()
}

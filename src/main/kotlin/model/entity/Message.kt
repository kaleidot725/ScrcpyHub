package model.entity

sealed class Message {
    data class SuccessToSaveScreenshot(val context: Device.Context, val fileName: String) : Message()
    data class FailedToSaveScreenshot(val context: Device.Context) : Message()
    object EmptyMessage : Message()
}

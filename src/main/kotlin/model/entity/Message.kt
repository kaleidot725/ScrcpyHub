package model.entity

sealed class Message {
    data class SuccessToSaveScreenshot(val device: Device, val fileName: String) : Message()
    data class FailedToSaveScreenshot(val device: Device) : Message()
    object EmptyMessage : Message()
}

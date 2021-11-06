package model.entity

sealed class Message {
    object SaveScreenshotSuccessMessage : Message()
    object SaveScreenshotFailedMessage : Message()
    object EmptyMessage : Message()
}
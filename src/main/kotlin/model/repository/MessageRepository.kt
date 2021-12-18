package model.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import model.entity.Message

class MessageRepository {
    private val _message: MutableSharedFlow<Message> =
        MutableSharedFlow(replay = 0, extraBufferCapacity = 5, BufferOverflow.SUSPEND)
    val message: SharedFlow<Message> = _message

    suspend fun push(message: Message) {
        _message.emit(message)
    }
}

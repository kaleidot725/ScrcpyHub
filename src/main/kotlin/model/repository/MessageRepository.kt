package model.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import model.entity.Message

class MessageRepository {
    private val _notifyMessage: MutableSharedFlow<Message.Notify> = MutableSharedFlow(replay = 0)
    val notifyMessage: SharedFlow<Message.Notify> = _notifyMessage

    private var latestErrorMessages: Set<Message.Error> = setOf()
    private val _errorMessages: MutableSharedFlow<Set<Message.Error>> = MutableSharedFlow(replay = 0)
    val errorMessages: SharedFlow<Set<Message.Error>> = _errorMessages

    suspend fun notify(message: Message.Notify) {
        _notifyMessage.emit(message)
    }

    suspend fun pushError(message: Message.Error) {
        val newErrorMessages = latestErrorMessages.toMutableSet()
        newErrorMessages.add(message)
        _errorMessages.emit(newErrorMessages)
        latestErrorMessages = newErrorMessages
    }

    suspend fun popError(message: Message.Error) {
        val newErrorMessages = latestErrorMessages.toMutableSet()
        newErrorMessages.remove(message)
        _errorMessages.emit(newErrorMessages)
        latestErrorMessages = newErrorMessages
    }
}

package model.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import model.entity.Message

class MessageRepository {
    private val _notifyMessage: MutableSharedFlow<Message.Notify> = MutableSharedFlow(replay = 0)
    val notifyMessage: SharedFlow<Message.Notify> = _notifyMessage

    private var latestErrorMessages: Set<Message.Error> = setOf()
    private val _errorMessages: MutableStateFlow<Set<Message.Error>> = MutableStateFlow(emptySet())
    val errorMessages: StateFlow<Set<Message.Error>> = _errorMessages

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

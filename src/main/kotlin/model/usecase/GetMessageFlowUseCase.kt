package model.usecase

import kotlinx.coroutines.flow.SharedFlow
import model.entity.Message
import model.repository.MessageRepository

class GetMessageFlowUseCase(
    private val messageRepository: MessageRepository
) {
    fun execute(): SharedFlow<Message> {
        return messageRepository.message
    }
}

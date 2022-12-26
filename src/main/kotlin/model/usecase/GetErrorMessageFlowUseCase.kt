package model.usecase

import kotlinx.coroutines.flow.StateFlow
import model.entity.Message
import model.repository.MessageRepository

class GetErrorMessageFlowUseCase(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(): StateFlow<Set<Message.Error>> {
        return messageRepository.errorMessages
    }
}

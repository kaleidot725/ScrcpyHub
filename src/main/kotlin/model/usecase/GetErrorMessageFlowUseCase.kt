package model.usecase

import kotlinx.coroutines.flow.SharedFlow
import model.entity.Message
import model.repository.MessageRepository

class GetErrorMessageFlowUseCase(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(): SharedFlow<Set<Message.Error>> {
        return messageRepository.errorMessages
    }
}

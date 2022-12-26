package model.usecase

import kotlinx.coroutines.flow.SharedFlow
import model.entity.Message
import model.repository.MessageRepository

class GetNotifyMessageFlowUseCase(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(): SharedFlow<Message.Notify> {
        return messageRepository.notifyMessage
    }
}

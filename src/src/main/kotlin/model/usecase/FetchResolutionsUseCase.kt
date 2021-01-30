package model.usecase

import model.entity.Resolution
import model.repository.ResolutionRepository

class FetchResolutionsUseCase(private val resolutionRepository: ResolutionRepository) {
    fun execute(): List<Resolution> {
        return resolutionRepository.getAll()
    }
}
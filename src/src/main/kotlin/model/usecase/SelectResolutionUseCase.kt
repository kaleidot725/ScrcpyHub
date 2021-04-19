package model.usecase

import model.entity.Resolution
import model.repository.ResolutionRepository

class SelectResolutionUseCase(private val resolutionRepository: ResolutionRepository) {
    fun execute(resolution: Resolution) {
        resolutionRepository.selected = resolution
    }
}
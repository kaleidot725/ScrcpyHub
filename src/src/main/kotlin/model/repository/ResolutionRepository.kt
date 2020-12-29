package model.repository

import model.Resolution

class ResolutionRepository {
    fun getAll(): List<Resolution> = RESOLUTION_LIST

    companion object {
        val RESOLUTION_LIST = listOf(
            Resolution("Full HD", 1920, 1080),
            Resolution("HD", 1280, 720)
        )
    }
}
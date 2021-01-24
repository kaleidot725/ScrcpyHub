package model.repository

import model.Resolution

class ResolutionRepository {
    var selected: Resolution? = null

    fun getAll(): List<Resolution> = RESOLUTION_LIST

    companion object {
        val RESOLUTION_LIST = listOf(
            Resolution("FHD", 1920, 1080),
            Resolution("HD", 1280, 720),
            Resolution("WSVGA", 1024, 576),
            Resolution("FWVGA", 854, 480),
            Resolution("QVGA", 320, 240)
        )
    }
}
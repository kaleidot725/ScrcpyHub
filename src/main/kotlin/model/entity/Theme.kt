package model.entity

import kotlinx.serialization.Serializable

@Serializable
enum class Theme {
    LIGHT,
    DARK,
    SYNC_WITH_OS
}

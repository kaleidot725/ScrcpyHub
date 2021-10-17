package model.entity

data class Device(val id: String, val name: String, var customName: String? = null) {
    val displayName get() = customName ?: name
}

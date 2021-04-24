package resource

object Navigation {
    const val DEVICES_PAGE: String = "Devices"
    const val SETTING_PAGE: String = "Setting"
    const val DEFAULT_PAGE: String = DEVICES_PAGE
    
    val PAGE_NAMES: List<String> = listOf(
        DEVICES_PAGE, SETTING_PAGE
    )
}
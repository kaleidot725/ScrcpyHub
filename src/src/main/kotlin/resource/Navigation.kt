package resource

object Navigation {
    val DEVICES_PAGE: Root = Root("Device", Images.DEVICE_BLACK)
    val SETTING_PAGE: Root = Root("Setting", Images.SETTING)
    val DEFAULT_PAGE: Root = DEVICES_PAGE

    val PAGE_NAMES: List<Root> = listOf(
        DEVICES_PAGE, SETTING_PAGE
    )

    data class Root(
        val name: String,
        val iconRes: String
    )
}
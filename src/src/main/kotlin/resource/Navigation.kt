package resource

object Navigation {
    val DEVICES_PAGE: Root = Root("Devices")
    val DEVICE_PAGE: Root = Root("Device")
    val SETTING_PAGE: Root = Root("Setting")

    data class Root(val name: String)
}
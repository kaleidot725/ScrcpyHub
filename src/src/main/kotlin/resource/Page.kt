package resource

import model.entity.Device

sealed class Page(val name: String) {
    object DevicesPage : Page("Devices")
    data class DevicePage(val device: Device) : Page("Device")
    object SettingPage : Page("Preferences")
}
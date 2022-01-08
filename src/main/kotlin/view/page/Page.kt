package view.page

import model.entity.Device

sealed class Page(val name: String) {
    object DevicesPage : Page("Devices")
    data class DevicePage(val context: Device.Context) : Page("Device")
    object SettingPage : Page("Preferences")
}

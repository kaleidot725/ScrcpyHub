package view.components.pages

import model.entity.Device

sealed class Page(val name: String) {
    object LoadingPage : Page("Loading")
    object DevicesPage : Page("Devices")
    data class DevicePage(val context: Device.Context) : Page("Device")
    object SettingPage : Page("Preferences")
}

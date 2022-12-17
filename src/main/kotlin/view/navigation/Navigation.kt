package view.navigation

import model.entity.Device

sealed class Navigation(val name: String) {
    object DevicesPage : Navigation("Devices")
    data class DevicePage(val context: Device.Context) : Navigation("Device")
    object SettingPage : Navigation("Preferences")
}

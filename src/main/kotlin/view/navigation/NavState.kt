package view.navigation

import model.entity.Device

sealed class NavState(val name: String) {
    object LoadingPage : NavState("Loading")
    object DevicesPage : NavState("Devices")
    data class DevicePage(val context: Device.Context) : NavState("Device")
    object SettingPage : NavState("Preferences")
}

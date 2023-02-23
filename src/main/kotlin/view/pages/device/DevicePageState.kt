package view.pages.device

import model.entity.Device

data class DevicePageState(
    val titleName: String = "",
    val editName: String = "",
    val maxSize: String = "",
    val maxSizeError: String = "",
    val maxFrameRate: String = "",
    val maxFrameRateError: String = "",
    val bitrate: String = "",
    val bitrateError: String = "",
    val lockOrientation: Device.Context.LockOrientation = Device.Context.LockOrientation.NONE,
    val enableBorderless: Boolean = false,
    val enableAlwaysOnTop: Boolean = false,
    val enableFullScreen: Boolean = false,
    val rotation: Device.Context.Rotation = Device.Context.Rotation.NONE,
    val savable: Boolean = false,
) {
    val lockOrientations: List<Device.Context.LockOrientation> = Device.Context.LockOrientation.values().toList()
    val rotations: List<Device.Context.Rotation> = Device.Context.Rotation.values().toList()
}

package view.pages.device

import model.entity.Device

data class DevicePageState(
    val titleName: String = "",
    val editName: String = "",
    val enableStayAwake: Boolean = false,
    val enableShowTouches: Boolean = false,
    val enablePowerOffOnClose: Boolean = false,
    val disablePowerOnOnStart: Boolean = false,
    val maxSize: String = "",
    val maxSizeError: String = "",
    val maxFrameRate: String = "",
    val maxFrameRateError: String = "",
    val bitrate: String = "",
    val bitrateError: String = "",
    val buffering: String = "",
    val bufferingError: String = "",
    val noAudio: Boolean = false,
    val audioBitrate: String = "",
    val audioBitrateError: String = "",
    val audioBuffering: String = "",
    val audioBufferingError: String = "",
    val captureOrientation: Device.Context.CaptureOrientation = Device.Context.CaptureOrientation.NONE,
    val enableBorderless: Boolean = false,
    val enableAlwaysOnTop: Boolean = false,
    val enableFullScreen: Boolean = false,
    val orientation: Device.Context.Orientation = Device.Context.Orientation.NONE,
    val enableHidKeyboard: Boolean = false,
    val enableHidMouse: Boolean = false,
    val savable: Boolean = false,
) {
    val captureOrientations: List<Device.Context.CaptureOrientation> = Device.Context.CaptureOrientation.values().toList()
    val orientations: List<Device.Context.Orientation> = Device.Context.Orientation.values().toList()
}

package view.resource

object Strings {
    // COMMON
    const val APP_NAME = "ScrcpyHub | v2.0.0"
    const val SAVE = "Save"
    const val CANCEL = "Cancel"
    const val QUIT = "Quit"

    // TRAY
    const val TRAY_SHOW_SCRCPY_HUB = "Show Window"
    const val TRAY_ENABLE_ALWAYS_TOP = "Enable Always on Top"
    const val TRAY_ABOUT_LICENSE = "About License"

    // Error
    const val NOT_FOUND_ADB_COMMAND = "Not found ADB binary"
    const val NOT_FOUND_SCRCPY_COMMAND = "Not found Scrcpy binary"

    // License Dialog
    const val LICENSE_TITLE = "About Libraries"
    
    // DEVICES PAGE
    const val DEVICES_PAGE_START_MIRRORING = "Start Mirroring"
    const val DEVICES_PAGE_STOP_MIRRORING = "Stop Mirroring"
    const val DEVICES_PAGE_START_RECORDING = "Start Recording"
    const val DEVICES_PAGE_STOP_RECORDING = "Stop Recording"
    const val DEVICES_PAGE_NOT_FOUND_DEVICES = "Not found devices"
    const val DEVICES_PAGE_ERROR_STARTING_ADB_SERVER = "Can't start adb server"

    // Device Page
    const val DEVICE_PAGE_EDIT_DEVICE_TITLE = "Device"
    const val DEVICE_PAGE_EDIT_NAME_TITLE = "Custom Name"
    const val DEVICE_PAGE_EDIT_NAME_DETAILS = "To customize your device name."
    const val DEVICE_PAGE_EDIT_VIDEO_TITLE = "Video"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_TITLE = "Reduce size"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS = "To limit both the width and height."
    const val DEVICE_PAGE_EDIT_MAX_FRAME_RATE_TITLE = "Limit frame rate"
    const val DEVICE_PAGE_EDIT_MAX_FRAME_RATE_DETAILS = "The capture frame rate can be limited."
    const val DEVICE_PAGE_EDIT_MAX_BITRATE_TITLE = "Change bit-rate (Mbps)"
    const val DEVICE_PAGE_EDIT_MAX_BITRATE_DETAILS = "To change the video bitrate."
    const val DEVICE_PAGE_EDIT_ORIENTATION_TITLE = "Lock Orientation (Affect Recording)"
    const val DEVICE_PAGE_EDIT_ORIENTATION_NONE = "None"
    const val DEVICE_PAGE_EDIT_ORIENTATION_NATURAL = "Natural"
    const val DEVICE_PAGE_EDIT_ORIENTATION_COUNTER_CLOCK_WISE_90 = "90° Counter Clock Wise"
    const val DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_180 = "180°"
    const val DEVICE_PAGE_EDIT_ORIENTATION_CLOCK_WISE_90 = "90° Clock Wise"
    const val DEVICE_PAGE_EDIT_WINDOW_TITLE = "Window"
    const val DEVICE_PAGE_EDIT_BORDERLESS_TITLE = "Borderless"
    const val DEVICE_PAGE_EDIT_BORDERLESS_DETAILS = "To disable window decorations"
    const val DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_TITLE = "Always on Top"
    const val DEVICE_PAGE_EDIT_ALWAYS_ON_TOP_DETAILS = "To keep the scrcpy window always on top"
    const val DEVICE_PAGE_EDIT_FULLSCREEN_TITLE = "Fullscreen"
    const val DEVICE_PAGE_EDIT_FULLSCREEN_DETAILS = "The app may be started directly in fullscreen"
    const val DEVICE_PAGE_EDIT_ROTATION_TITLE = "Rotation (Don't Affect Recording)"
    const val DEVICE_PAGE_EDIT_ROTATION_NONE = "None"
    const val DEVICE_PAGE_EDIT_ROTATION_COUNTER_CLOCK_WISE_90 = "90° Counter Clock Wise"
    const val DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_180 = "180°"
    const val DEVICE_PAGE_EDIT_ROTATION_CLOCK_WISE_90 = "90° Clock Wise"

    // Setting Page
    const val SETTING_PAGE_TITLE = "Preferences"
    const val SETTING_PAGE_EDIT_THEME_TITLE = "Theme"
    const val SETTING_PAGE_EDIT_BINARY_TITLE = "Binary"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_TITLE = "ADB Location"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS = "If it is empty, use an environment variable"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE = "Scrcpy Location"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS = "If it is empty, use an environment variable"
    const val SETTING_PAGE_EDIT_CAPTURE_AND_RECORD_TITLE = "Capture & Recording"
    const val SETTING_PAGE_EDIT_SCREEN_SHOT_TITLE = "Capture Directory"
    const val SETTING_PAGE_EDIT_SCREEN_SHOT_DETAILS = "If it is empty, save file to desktop"
    const val SETTING_PAGE_EDIT_SCREEN_RECORD_TITLE = "Recording Directory"
    const val SETTING_PAGE_EDIT_SCREEN_RECORD_DETAILS = "If it is empty, save file to desktop"

    const val SETTING_THEME_LIGHT = "Light"
    const val SETTING_THEME_DARK = "Dark"
    const val SETTING_THEME_SYNC_WITH_OS = "Auto"
}

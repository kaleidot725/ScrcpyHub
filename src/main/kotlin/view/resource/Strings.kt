package view.resource

object Strings {
    // COMMON
    const val APP_NAME = "ScrcpyHub"
    const val SAVE = "Save"
    const val QUIT = "Quit"

    // TRAY
    const val TRAY_SHOW_SCRCPY_HUB = "Display Window"
    const val TRAY_ENABLE_ALWAYS_TOP = "Enable Always on Top"

    // Error
    const val NOT_FOUND_ADB_COMMAND = "Not found ADB binary"
    const val NOT_FOUND_SCRCPY_COMMAND = "Not found Scrcpy binary"

    // DEVICES PAGE
    const val DEVICES_PAGE_START_MIRRORING = "Start Mirroring"
    const val DEVICES_PAGE_STOP_MIRRORING = "Stop Mirroring"
    const val DEVICES_PAGE_START_RECORDING = "Start Recording"
    const val DEVICES_PAGE_STOP_RECORDING = "Stop Recording"
    const val DEVICES_PAGE_NOT_FOUND_DEVICES = "Not found devices"
    const val DEVICES_PAGE_ERROR_STARTING_ADB_SERVER = "Can't start adb server"

    // Device Page
    const val DEVICE_PAGE_EDIT_NAME_TITLE = "Device Name"
    const val DEVICE_PAGE_EDIT_NAME_DETAILS = "Customize device name"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_TITLE = "Max Video Size"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS = "Set video width and height limit"
    const val DEVICE_PAGE_EDIT_MAX_FRAME_RATE_TITLE = "FPS (frame per second)"
    const val DEVICE_PAGE_EDIT_MAX_FRAME_RATE_DETAILS = "Set video fps limit"
    const val DEVICE_PAGE_EDIT_MAX_BITRATE_TITLE = "Bitrate (Mbps)"
    const val DEVICE_PAGE_EDIT_MAX_BITRATE_DETAILS = "Set video bitrate limit"

    // Setting Page
    const val SETTING_PAGE_TITLE = "Preferences"
    const val SETTING_PAGE_EDIT_THEME_TITLE = "Theme"
    const val SETTING_PAGE_EDIT_THEME_DETAILS = "Select application theme"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_TITLE = "ADB Binary Location"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS = "If it is empty, use an environment variable"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE = "Scrcpy Binary Location"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS = "If it is empty, use an environment variable"

    const val SETTING_THEME_LIGHT = "Light"
    const val SETTING_THEME_DARK = "Dark"
    const val SETTING_THEME_SYNC_WITH_OS = "Auto"
}

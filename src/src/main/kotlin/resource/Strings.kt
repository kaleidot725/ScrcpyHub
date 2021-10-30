package resource

object Strings {
    // COMMON
    const val APP_VERSION = "1.0.0"
    const val APP_NAME = "ScrcpyHub"
    const val SAVE = "Save"
    const val SETUP = "Setup"
    const val QUIT = "Quit"

    // HOME
    const val HOME_TITLE = "HOME"

    // TRAY
    const val TRAY_TOGGLE_SCRCPY_HUB = "Toggle ScrcpyHub"
    const val TRAY_VERSION = "$TRAY_TOGGLE_SCRCPY_HUB v$APP_VERSION"

    // Error
    const val NOT_FOUND_ADB_COMMAND = "Not found ADB Command"
    const val NOT_FOUND_SCRCPY_COMMAND = "Not found Scrcpy Command"

    // DEVICES PAGE
    const val DEVICES_PAGE_START = "START"
    const val DEVICES_PAGE_STOP = "STOP"
    const val DEVICES_PAGE_NOT_FOUND_DEVICES = "Not found devices"

    const val DEVICES_DROP_DOWN_SETTING_MENU_TITLE = "Setting"
    const val DEVICES_DROP_DOWN_QUIT_MENU_TITLE = "Quit"

    // Device Page
    const val DEVICE_PAGE_EDIT_NAME_TITLE = "Device Name"
    const val DEVICE_PAGE_EDIT_NAME_DETAILS = "Display device name"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_TITLE = "Max Size"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS = "Video width and height size limit"

    // Setting Page
    const val SETTING_PAGE_TITLE = "Setting"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_TITLE = "ADB Location"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS = "If it is empty, use an environment variable"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE = "Scrcpy Location"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS = "If it is empty, use an environment variable"
}

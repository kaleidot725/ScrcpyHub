package resource

object Strings {
    // COMMON
    const val APP_VERSION = "1.2.2"
    const val APP_NAME = "ScrcpyHub"
    const val SAVE = "Save"
    const val SETUP = "Setup"
    const val QUIT = "Quit"

    // TRAY
    const val TRAY_TOGGLE_SCRCPY_HUB = "Toggle ScrcpyHub"
    const val TRAY_VERSION = "Version v$APP_VERSION"

    // Error
    const val NOT_FOUND_ADB_COMMAND = "Not found ADB Command"
    const val NOT_FOUND_SCRCPY_COMMAND = "Not found Scrcpy Command"

    // DEVICES PAGE
    const val DEVICES_PAGE_START = "Start"
    const val DEVICES_PAGE_STOP = "Stop"
    const val DEVICES_PAGE_NOT_FOUND_DEVICES = "Not found devices"
    const val DEVICES_DROP_DOWN_PREFERENCE_MENU_TITLE = "Preferences"
    const val DEVICES_DROP_DOWN_QUIT_MENU_TITLE = "Quit"
    const val DEVICE_DROP_DOWN_PREFERENCE_MENU_TITLE = "Edit Device Setting"
    const val DEVICE_DROP_DOWN_SCREEN_SHOT_MENU_TITLE = "Save Screenshot to Desktop"

    // Device Page
    const val DEVICE_PAGE_EDIT_NAME_TITLE = "Device Name"
    const val DEVICE_PAGE_EDIT_NAME_DETAILS = "Display device name"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_TITLE = "Max Size"
    const val DEVICE_PAGE_EDIT_MAX_SIZE_DETAILS = "Video width and height size limit"
    const val DEVICE_PAGE_EDIT_RECORDING_TITLE = "Enable Recording"
    const val DEVICE_PAGE_EDIT_RECORDING_DETAILS = "Start record when mirroring"

    // Setting Page
    const val SETTING_PAGE_TITLE = "Preferences"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_TITLE = "ADB Location"
    const val SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS = "If it is empty, use an environment variable"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE = "Scrcpy Location"
    const val SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS = "If it is empty, use an environment variable"
}

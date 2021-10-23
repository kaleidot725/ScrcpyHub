package resource

object Strings {
    // COMMON
    const val APP_VERSION = "1.0.0"
    const val APP_NAME = "ScrcpyHub"

    // HOME
    const val HOME_TITLE = "HOME"

    // TRAY
    const val TRAY_TOGGLE_SCRCPY_HUB = "Toggle ScrcpyHub"
    const val TRAY_VERSION = "$TRAY_TOGGLE_SCRCPY_HUB v$APP_VERSION"
    const val TRAY_QUIT = "Quit"

    // DEVICES PAGE
    const val RUN = "START"
    const val STOP = "STOP"
    const val NO_ANDROID_DEVICE = "No Android Device."

    // Device Page
    const val DEVICE_NAME_SETTING = "Device Name"
    const val EDIT_DEVICE_NAME = "Display device name"
    const val DEVICE_RESOLUTION_SETTING = "Max Size"
    const val EDIT_RESOLUTION_NAME = "Video width and height size limit"

    // Setting Page
    const val SETTING_PAGE_TITLE = "Setting"
    const val ADB_LOCATION = "ADB Location"
    const val IF_ADB_LOCATION_IS_EMPTY = "If it is empty, use an environment variable"
    const val SCRCPY_LOCATION = "Scrcpy Location"
    const val IF_SCRCPY_LOCATION_IS_EMPTY = "If it is empty, use an environment variable"
    const val SAVE = "Save"

    // Error Message
    const val NOT_FOUND_ADB_COMMAND = "Not found ADB Command."
    const val NOT_FOUND_SCRCPY_COMMAND = "Not found Scrcpy Command."
    const val SETUP = "Setup"
}

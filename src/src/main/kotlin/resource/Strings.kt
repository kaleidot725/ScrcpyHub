package resource

object Strings {
    const val APP_VERSION = "1.0.0"

    // Action
    const val RUN = "START"
    const val STOP = "STOP"
    const val NO_ANDROID_DEVICE = "No Android Device."

    // Setting Page
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

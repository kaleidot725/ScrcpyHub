package model.os

enum class OSType {
    MAC_OS,
    LINUX,
    WINDOWS,
}

fun getOSType(): OSType {
    return when (System.getProperty("os.name")) {
        "Mac OS X" -> OSType.MAC_OS
        "Linux" -> OSType.LINUX
        else -> OSType.WINDOWS
    }
}
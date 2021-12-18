package common

enum class OsType {
    MAC_OS,
    WINDOWS
}

fun osType(): OsType {
    return when (System.getProperty("os.name")) {
        "Mac OS X" -> OsType.MAC_OS
        else -> OsType.WINDOWS
    }
}

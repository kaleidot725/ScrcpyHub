package model.os

interface OSContext {
    val type: OSType
    val settingPath: String
    val desktopPath: String
}

class OSContextForMac : OSContext {
    override val type: OSType = OSType.MAC_OS
    override val settingPath: String =
        System.getProperty("user.home") + "/Library/Application Support/ScrcpyHub/"
    override val desktopPath: String =
        System.getProperty("user.home") + "/Desktop/"
}

class OSContextForLinux : OSContext {
    override val type: OSType = OSType.LINUX
    override val settingPath: String = System.getProperty("user.home") + "/AppData/ScrcpyHub"
    override val desktopPath: String = System.getProperty("user.home") + "/Desktop/"
}

class OSContextForWindows : OSContext {
    override val type: OSType = OSType.WINDOWS
    override val settingPath: String = System.getProperty("user.home") + "/.config/ScrcpyHub/"
    override val desktopPath: String = System.getProperty("user.home") + "/Desktop/"
}

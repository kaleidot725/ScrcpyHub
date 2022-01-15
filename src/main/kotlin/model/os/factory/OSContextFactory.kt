package model.os.factory

import model.os.OSContext
import model.os.OSType

class OSContextFactory {
    companion object {
//        single(named("setting_directory")) {
//            when (getOSType()) {
//                OSType.MAC_OS -> "/Library/Application Support/ScrcpyHub/"
//                OSType.LINUX -> "/ScrcpyHub/"
//                OSType.WINDOWS -> "./"
//            }
//        }

        fun create(): OSContext {
            return when (getOSType()) {
                OSType.MAC_OS -> object : OSContext {
                    override val type: OSType = OSType.MAC_OS
                    override val settingPath: String = ""
                    override val desktopPath: String = ""
                }
                OSType.LINUX -> object : OSContext {
                    override val type: OSType = OSType.LINUX
                    override val settingPath: String = ""
                    override val desktopPath: String = ""
                }
                OSType.WINDOWS -> object : OSContext {
                    override val type: OSType = OSType.WINDOWS
                    override val settingPath: String = ""
                    override val desktopPath: String = ""
                }
            }
        }

        private fun getOSType(): OSType {
            return when (System.getProperty("os.name")) {
                "Mac OS X" -> OSType.MAC_OS
                "Linux" -> OSType.LINUX
                else -> OSType.WINDOWS
            }
        }
    }
}
package model.command.factory

import common.OsType

interface KillCommandFactory {
    fun create(pid: Long): List<String>

    private class KillCommandFactoryForMac : KillCommandFactory {
        @OptIn(kotlin.ExperimentalStdlibApi::class)
        override fun create(pid: Long): List<String> {
            return buildList {
                add("kill")
                add("-SIGINT")
                add(pid.toString())
            }
        }
    }

    private class KillCommandFactoryForWindows : KillCommandFactory {
        @OptIn(kotlin.ExperimentalStdlibApi::class)
        override fun create(pid: Long): List<String> {
            return buildList {
                add("taskkill")
                add("/PID")
                add(pid.toString())
            }
        }
    }

    companion object {
        fun get(osType: OsType): KillCommandFactory {
            return when (osType) {
                OsType.MAC_OS -> KillCommandFactoryForMac()
                OsType.WINDOWS -> KillCommandFactoryForWindows()
            }
        }
    }
}


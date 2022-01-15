package model.command.factory

import model.os.OSContext
import model.os.OSType

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

    private class KillCommandFactoryForLinux : KillCommandFactory {
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
        fun create(context: OSContext): KillCommandFactory {
            return when (context.type) {
                OSType.MAC_OS -> KillCommandFactoryForMac()
                OSType.LINUX -> KillCommandFactoryForLinux()
                OSType.WINDOWS -> KillCommandFactoryForWindows()
            }
        }
    }
}

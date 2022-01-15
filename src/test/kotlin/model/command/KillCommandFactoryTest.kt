package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.command.factory.KillCommandFactory
import model.os.OSContext
import model.os.OSType

class KillCommandFactoryTest : StringSpec(
    {
        "createForWindows" {
            val factory = KillCommandFactory.create(
                object : OSContext {
                    override val type: OSType = OSType.WINDOWS
                    override val desktopPath: String = ""
                    override val settingPath: String = ""
                }
            )
            factory.create(0) shouldBe listOf("taskkill", "/PID", "0")
        }
        "createForMacOs" {
            val factory = KillCommandFactory.create(
                object : OSContext {
                    override val type: OSType = OSType.MAC_OS
                    override val desktopPath: String = ""
                    override val settingPath: String = ""
                }
            )
            factory.create(0) shouldBe listOf("kill", "-SIGINT", "0")
        }
        "createForLinux" {
            val factory = KillCommandFactory.create(
                object : OSContext {
                    override val type: OSType = OSType.LINUX
                    override val desktopPath: String = ""
                    override val settingPath: String = ""
                }
            )
            factory.create(0) shouldBe listOf("kill", "-SIGINT", "0")
        }
    }
)

package model.command

import common.OsType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.command.factory.KillCommandFactory

class KillCommandFactoryTest : StringSpec(
    {
        "createForWindows" {
            val factory = KillCommandFactory.get(OsType.WINDOWS)
            factory.create(0) shouldBe listOf("taskkill", "/PID", "0")
        }
        "createForMacOs" {
            val factory = KillCommandFactory.get(OsType.MAC_OS)
            factory.create(0) shouldBe listOf("kill", "-SIGINT", "0")
        }
    }
)

package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class KillCommandCreatorTest : StringSpec(
    {
        "createForWindows" {
            val factory = KillCommandCreatorForWindows()
            factory.create(0) shouldBe listOf("taskkill", "/PID", "0")
        }
        "createForMacOs" {
            val factory = KillCommandCreatorForMacOS()
            factory.create(0) shouldBe listOf("kill", "-SIGINT", "0")
        }
        "createForLinux" {
            val factory = KillCommandCreatorForLinux()
            factory.create(0) shouldBe listOf("kill", "-SIGINT", "0")
        }
    },
)

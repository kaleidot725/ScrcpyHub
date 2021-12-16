package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.command.factory.AdbCommandFactory

class AdbCommandFactoryTest : StringSpec(
    {
        "create" {
            val factory = AdbCommandFactory(path = "test/")
            factory.create() shouldBe listOf("test/adb")
        }
        "create_when_no_path_specified" {
            val factory = AdbCommandFactory()
            factory.create() shouldBe listOf("adb")
        }
        "create_help" {
            val factory = AdbCommandFactory(path = "test/")
            factory.createHelp() shouldBe listOf("test/adb", "--help")
        }
        "create_help_when_no_path_specified" {
            val factory = AdbCommandFactory()
            factory.createHelp() shouldBe listOf("adb", "--help")
        }
    }
)

package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.command.factory.ScrcpyCommandFactory
import model.entity.Device

class ScrcpyCommandFactoryTest : StringSpec(
    {
        "create" {
            val factory = ScrcpyCommandFactory(path = "test/")

            val device1 = Device(
                id = "DEVICE1", name = "NAME", Device.Setting(customName = "CUSTOM_NAME", maxSize = null)
            )
            factory.create(device1) shouldBe listOf("test/scrcpy", "-s", "DEVICE1")

            val device2 = Device(
                id = "DEVICE2", name = "NAME", Device.Setting(customName = "CUSTOM_NAME", maxSize = 1000)
            )
            factory.create(device2) shouldBe listOf("test/scrcpy", "-s", "DEVICE2", "-m", "1000")
        }
        "create_when_no_path_specified" {
            val factory = ScrcpyCommandFactory()

            val device1 = Device(
                id = "DEVICE1", name = "NAME", Device.Setting(customName = "CUSTOM_NAME", maxSize = null)
            )
            factory.create(device1) shouldBe listOf("scrcpy", "-s", "DEVICE1")

            val device2 = Device(
                id = "DEVICE2", name = "NAME", Device.Setting(customName = "CUSTOM_NAME", maxSize = 1000)
            )
            factory.create(device2) shouldBe listOf("scrcpy", "-s", "DEVICE2", "-m", "1000")
        }
        "create_help" {
            val factory = ScrcpyCommandFactory(path = "test/")
            factory.createHelp() shouldBe listOf("test/scrcpy", "-h")
        }
        "create_help_when_no_path_specified" {
            val factory = ScrcpyCommandFactory()
            factory.createHelp() shouldBe listOf("scrcpy", "-h")
        }
    }
)

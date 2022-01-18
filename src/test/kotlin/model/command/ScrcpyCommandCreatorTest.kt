package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.command.creator.ScrcpyCommandCreator
import model.entity.Device

class ScrcpyCommandCreatorTest : StringSpec({
    "create" {
        val factory = ScrcpyCommandCreator(path = "test/")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), maxSize = null
        )
        factory.create(device1) shouldBe listOf("test/scrcpy", "-s", "DEVICE1")
        factoryWhenNoSeparator.create(device1) shouldBe listOf("test/scrcpy", "-s", "DEVICE1")

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), maxSize = 1000
        )
        factory.create(device2) shouldBe listOf("test/scrcpy", "-s", "DEVICE2", "-m", "1000")
        factoryWhenNoSeparator.create(device2) shouldBe listOf("test/scrcpy", "-s", "DEVICE2", "-m", "1000")
    }
    "create_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), customName = "CUSTOM_NAME", maxSize = null
        )
        factory.create(device1) shouldBe listOf("scrcpy", "-s", "DEVICE1")

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), customName = "CUSTOM_NAME", maxSize = 1000
        )
        factory.create(device2) shouldBe listOf("scrcpy", "-s", "DEVICE2", "-m", "1000")
    }
    "create_record" {
        val factory = ScrcpyCommandCreator(path = "test/")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), maxSize = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "test/scrcpy", "-s", "DEVICE1", "-r", "fileName1"
        )
        factoryWhenNoSeparator.createRecord(device1, "fileName1") shouldBe listOf(
            "test/scrcpy", "-s", "DEVICE1", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), maxSize = 1000
        )
        factory.createRecord(device2, "fileName2") shouldBe listOf(
            "test/scrcpy", "-s", "DEVICE2", "-m", "1000", "-r", "fileName2"
        )
        factoryWhenNoSeparator.createRecord(device2, "fileName2") shouldBe listOf(
            "test/scrcpy", "-s", "DEVICE2", "-m", "1000", "-r", "fileName2"
        )
    }
    "create_record_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), customName = "CUSTOM_NAME", maxSize = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "scrcpy", "-s", "DEVICE1", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), customName = "CUSTOM_NAME", maxSize = 1000
        )
        factory.createRecord(device2, "fileName2") shouldBe listOf(
            "scrcpy", "-s", "DEVICE2", "-m", "1000", "-r", "fileName2"
        )
    }
    "create_help" {
        val factory = ScrcpyCommandCreator(path = "test/")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        factory.createHelp() shouldBe listOf("test/scrcpy", "-h")
        factoryWhenNoSeparator.createHelp() shouldBe listOf("test/scrcpy", "-h")
    }
    "create_help_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.createHelp() shouldBe listOf("scrcpy", "-h")
    }
})

package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.entity.Device
import java.io.File.separator as fileSeparator

class ScrcpyCommandCreatorTest : StringSpec({
    "create" {
        val factory = ScrcpyCommandCreator(path = "test$fileSeparator")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), maxSize = null, maxFrameRate = null, bitrate = null
        )
        factory.create(device1) shouldBe listOf("test${fileSeparator}scrcpy", "-s", "DEVICE1", "--window-title", "NAME")
        factoryWhenNoSeparator.create(device1) shouldBe listOf(
            "test${fileSeparator}scrcpy",
            "-s",
            "DEVICE1",
            "--window-title",
            "NAME"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), maxSize = 1000, maxFrameRate = 60, bitrate = 2
        )
        factory.create(device2) shouldBe
            listOf(
                "test${fileSeparator}scrcpy",
                "-s",
                "DEVICE2",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--window-title",
                "NAME"
            )
        factoryWhenNoSeparator.create(device2) shouldBe
            listOf(
                "test${fileSeparator}scrcpy",
                "-s",
                "DEVICE2",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--window-title",
                "NAME"
            )
    }
    "create_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), customName = "CUSTOM_NAME", maxSize = null, maxFrameRate = null
        )
        factory.create(device1) shouldBe listOf("scrcpy", "-s", "DEVICE1", "--window-title", "CUSTOM_NAME")

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"),
            customName = "CUSTOM_NAME",
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2
        )
        factory.create(device2) shouldBe listOf(
            "scrcpy",
            "-s",
            "DEVICE2",
            "-m",
            "1000",
            "--max-fps",
            "60",
            "-b",
            "2M",
            "--window-title",
            "CUSTOM_NAME"
        )
    }
    "create_record" {
        val factory = ScrcpyCommandCreator(path = "test")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"), maxSize = null, maxFrameRate = null, bitrate = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "test${fileSeparator}scrcpy", "-s", "DEVICE1", "--window-title", "NAME", "-r", "fileName1"
        )
        factoryWhenNoSeparator.createRecord(device1, "fileName1") shouldBe listOf(
            "test${fileSeparator}scrcpy", "-s", "DEVICE1", "--window-title", "NAME", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"), maxSize = 1000, maxFrameRate = 60, bitrate = 2
        )
        factory.createRecord(device2, "fileName2") shouldBe listOf(
            "test${fileSeparator}scrcpy",
            "-s",
            "DEVICE2",
            "-m",
            "1000",
            "--max-fps",
            "60",
            "-b",
            "2M",
            "--window-title",
            "NAME",
            "-r",
            "fileName2"
        )
        factoryWhenNoSeparator.createRecord(device2, "fileName2") shouldBe listOf(
            "test${fileSeparator}scrcpy",
            "-s",
            "DEVICE2",
            "-m",
            "1000",
            "--max-fps",
            "60",
            "-b",
            "2M",
            "--window-title",
            "NAME",
            "-r",
            "fileName2"
        )
    }
    "create_record_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1", name = "NAME"),
            customName = "CUSTOM_NAME",
            maxSize = null,
            maxFrameRate = null,
            bitrate = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "scrcpy", "-s", "DEVICE1", "--window-title", "CUSTOM_NAME", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2", name = "NAME"),
            customName = "CUSTOM_NAME",
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2
        )
        factory.createRecord(device2, "fileName2") shouldBe listOf(
            "scrcpy",
            "-s",
            "DEVICE2",
            "-m",
            "1000",
            "--max-fps",
            "60",
            "-b",
            "2M",
            "--window-title",
            "CUSTOM_NAME",
            "-r",
            "fileName2"
        )
    }
    "create_help" {
        val factory = ScrcpyCommandCreator(path = "test$fileSeparator")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(path = "test")

        factory.createHelp() shouldBe listOf("test${fileSeparator}scrcpy", "-h")
        factoryWhenNoSeparator.createHelp() shouldBe listOf("test${fileSeparator}scrcpy", "-h")
    }
    "create_help_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.createHelp() shouldBe listOf("scrcpy", "-h")
    }
})

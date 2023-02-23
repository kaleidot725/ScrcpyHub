package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.entity.Device
import java.io.File.separator as fileSeparator

class ScrcpyCommandCreatorTest : StringSpec({
    "create" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")

        val device1 = Device.Context(
            Device(id = "DEVICE1"),
            maxSize = null,
            maxFrameRate = null,
            bitrate = null,
            lockOrientation = null,
            enableBorderless = false,
            enableAlwaysOnTop = false,
            enableFullScreen = false,
            rotation = null
        )
        factory.create(device1) shouldBe listOf(
            "test${fileSeparator}scrcpy",
            "-s",
            "DEVICE1",
            "--window-title",
            "DEVICE1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2"),
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2,
            lockOrientation = 1,
            enableBorderless = true,
            enableAlwaysOnTop = true,
            enableFullScreen = true,
            rotation = 1
        )
        factory.create(device2) shouldBe listOf(
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
            "DEVICE2",
            "--lock-video-orientation=1",
            "--window-borderless",
            "--always-on-top",
            "--fullscreen",
            "--rotation=1",
        )
    }
    "create_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1"),
            customName = "CUSTOM_NAME",
            maxSize = null,
            maxFrameRate = null,
            lockOrientation = null,
            enableBorderless = false,
            enableAlwaysOnTop = false,
            enableFullScreen = false,
            rotation = null
        )
        factory.create(device1) shouldBe listOf("scrcpy", "-s", "DEVICE1", "--window-title", "CUSTOM_NAME")

        val device2 = Device.Context(
            Device(id = "DEVICE2"),
            customName = "CUSTOM_NAME",
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2,
            lockOrientation = 1,
            enableBorderless = true,
            enableAlwaysOnTop = true,
            enableFullScreen = true,
            rotation = 1
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
            "CUSTOM_NAME",
            "--lock-video-orientation=1",
            "--window-borderless",
            "--always-on-top",
            "--fullscreen",
            "--rotation=1",
        )
    }
    "create_record" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")

        val device1 = Device.Context(
            Device(id = "DEVICE1"),
            maxSize = null,
            maxFrameRate = null,
            bitrate = null,
            lockOrientation = null,
            enableBorderless = false,
            enableAlwaysOnTop = false,
            enableFullScreen = false,
            rotation = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "test${fileSeparator}scrcpy", "-s", "DEVICE1", "--window-title", "DEVICE1", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2"),
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2,
            lockOrientation = 1,
            enableBorderless = true,
            enableAlwaysOnTop = true,
            enableFullScreen = true,
            rotation = 1
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
            "DEVICE2",
            "--lock-video-orientation=1",
            "--window-borderless",
            "--always-on-top",
            "--fullscreen",
            "--rotation=1",
            "-r",
            "fileName2",
        )
    }
    "create_record_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()

        val device1 = Device.Context(
            Device(id = "DEVICE1"),
            customName = "CUSTOM_NAME",
            maxSize = null,
            maxFrameRate = null,
            bitrate = null,
            enableBorderless = false,
            enableAlwaysOnTop = false,
            enableFullScreen = false,
            rotation = null
        )
        factory.createRecord(device1, "fileName1") shouldBe listOf(
            "scrcpy", "-s", "DEVICE1", "--window-title", "CUSTOM_NAME", "-r", "fileName1"
        )

        val device2 = Device.Context(
            Device(id = "DEVICE2"),
            customName = "CUSTOM_NAME",
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2,
            lockOrientation = 1,
            enableBorderless = true,
            enableAlwaysOnTop = true,
            enableFullScreen = true,
            rotation = 1
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
            "--lock-video-orientation=1",
            "--window-borderless",
            "--always-on-top",
            "--fullscreen",
            "--rotation=1",
            "-r",
            "fileName2",
        )
    }
    "create_help" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")
        val factoryWhenNoSeparator = ScrcpyCommandCreator(scrcpyBinaryPath = "test")
        factory.createHelp() shouldBe listOf("test${fileSeparator}scrcpy", "-h")
    }
    "create_help_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.createHelp() shouldBe listOf("scrcpy", "-h")
    }
})

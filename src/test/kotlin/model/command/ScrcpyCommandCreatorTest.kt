package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.entity.Device
import java.io.File.separator as fileSeparator

class ScrcpyCommandCreatorTest : StringSpec({
    val device1 =
        Device.Context(
            Device(id = "DEVICE1"),
            maxSize = null,
            maxFrameRate = null,
            bitrate = null,
            buffering = null,
            noAudio = false,
            audioBitrate = null,
            audioBuffering = null,
            lockOrientation = null,
            enableBorderless = false,
            enableAlwaysOnTop = false,
            enableFullScreen = false,
            rotation = null,
        )

    val device2 =
        Device.Context(
            Device(id = "DEVICE2"),
            customName = "CUSTOM_NAME",
            enableStayAwake = true,
            enableShowTouches = true,
            enablePowerOffOnClose = true,
            disablePowerOnOnStart = true,
            maxSize = 1000,
            maxFrameRate = 60,
            bitrate = 2,
            buffering = 3,
            lockOrientation = 1,
            noAudio = true,
            audioBitrate = 4,
            audioBuffering = 5,
            enableBorderless = true,
            enableAlwaysOnTop = true,
            enableFullScreen = true,
            rotation = 1,
            enableHidKeyboard = true,
            enableHidMouse = true,
        )

    "create" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")
        factory.create(device1) shouldBe
            listOf(
                "test${fileSeparator}scrcpy",
                "-s",
                "DEVICE1",
                "--window-title",
                "DEVICE1",
            )
        factory.create(device2) shouldBe
            listOf(
                "test${fileSeparator}scrcpy",
                "-s",
                "DEVICE2",
                "--stay-awake",
                "--show-touches",
                "--power-off-on-close",
                "--no-power-on",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--video-buffer=3",
                "--no-audio",
                "--audio-bit-rate=4K",
                "--audio-buffer=5",
                "--window-title",
                "CUSTOM_NAME",
                "--capture-orientation=1",
                "--window-borderless",
                "--always-on-top",
                "--fullscreen",
                "--orientation=1",
                "--keyboard=uhid",
                "--mouse=uhid",
            )
    }
    "create_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.create(device1) shouldBe listOf("scrcpy", "-s", "DEVICE1", "--window-title", "DEVICE1")
        factory.create(device2) shouldBe
            listOf(
                "scrcpy",
                "-s",
                "DEVICE2",
                "--stay-awake",
                "--show-touches",
                "--power-off-on-close",
                "--no-power-on",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--video-buffer=3",
                "--no-audio",
                "--audio-bit-rate=4K",
                "--audio-buffer=5",
                "--window-title",
                "CUSTOM_NAME",
                "--capture-orientation=1",
                "--window-borderless",
                "--always-on-top",
                "--fullscreen",
                "--orientation=1",
                "--keyboard=uhid",
                "--mouse=uhid",
            )
    }
    "create_record" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")
        factory.createRecord(device1, "fileName1") shouldBe
            listOf(
                "test${fileSeparator}scrcpy", "-s", "DEVICE1", "--window-title", "DEVICE1", "-r", "fileName1",
            )
        factory.createRecord(device2, "fileName2") shouldBe
            listOf(
                "test${fileSeparator}scrcpy",
                "-s",
                "DEVICE2",
                "--stay-awake",
                "--show-touches",
                "--power-off-on-close",
                "--no-power-on",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--video-buffer=3",
                "--no-audio",
                "--audio-bit-rate=4K",
                "--audio-buffer=5",
                "--window-title",
                "CUSTOM_NAME",
                "--capture-orientation=1",
                "--window-borderless",
                "--always-on-top",
                "--fullscreen",
                "--orientation=1",
                "--keyboard=uhid",
                "--mouse=uhid",
                "-r",
                "fileName2",
            )
    }
    "create_record_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.createRecord(device1, "fileName1") shouldBe
            listOf(
                "scrcpy", "-s", "DEVICE1", "--window-title", "DEVICE1", "-r", "fileName1",
            )
        factory.createRecord(device2, "fileName2") shouldBe
            listOf(
                "scrcpy",
                "-s",
                "DEVICE2",
                "--stay-awake",
                "--show-touches",
                "--power-off-on-close",
                "--no-power-on",
                "-m",
                "1000",
                "--max-fps",
                "60",
                "-b",
                "2M",
                "--video-buffer=3",
                "--no-audio",
                "--audio-bit-rate=4K",
                "--audio-buffer=5",
                "--window-title",
                "CUSTOM_NAME",
                "--capture-orientation=1",
                "--window-borderless",
                "--always-on-top",
                "--fullscreen",
                "--orientation=1",
                "--keyboard=uhid",
                "--mouse=uhid",
                "-r",
                "fileName2",
            )
    }
    "create_help" {
        val factory = ScrcpyCommandCreator(scrcpyBinaryPath = "test${fileSeparator}scrcpy")
        factory.createHelp() shouldBe listOf("test${fileSeparator}scrcpy", "-h")
    }
    "create_help_when_no_path_specified" {
        val factory = ScrcpyCommandCreator()
        factory.createHelp() shouldBe listOf("scrcpy", "-h")
    }
})

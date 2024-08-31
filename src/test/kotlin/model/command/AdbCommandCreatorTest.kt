package model.command

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.File.separator as fileSeparator

class AdbCommandCreatorTest : StringSpec(
    {
        "create" {
            val factory = AdbCommandCreator(adbBinaryPath = "test${fileSeparator}adb")
            factory.create() shouldBe listOf("test${fileSeparator}adb")
        }
        "create_when_no_path_specified" {
            val factory = AdbCommandCreator()
            factory.create() shouldBe listOf("adb")
        }
        "create_help" {
            val factory = AdbCommandCreator(adbBinaryPath = "test${fileSeparator}adb")
            factory.createHelp() shouldBe listOf("test${fileSeparator}adb", "--help")
        }
        "create_help_when_no_path_specified" {
            val factory = AdbCommandCreator()
            factory.createHelp() shouldBe listOf("adb", "--help")
        }
        "create_start_server" {
            val factory = AdbCommandCreator(adbBinaryPath = "test${fileSeparator}adb")
            factory.createStartServer() shouldBe listOf("test${fileSeparator}adb", "start-server")
        }
        "create_start_server_when_no_path_specified" {
            val factory = AdbCommandCreator()
            factory.createStartServer() shouldBe listOf("adb", "start-server")
        }
    },
)

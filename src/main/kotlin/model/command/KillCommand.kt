package model.command

import model.command.factory.KillCommandFactory

class KillCommand(private val factory: KillCommandFactory) {
    fun run(pid: Int): Process {
        val command = factory.create(pid)
        return ProcessBuilder(command).start()
    }
}
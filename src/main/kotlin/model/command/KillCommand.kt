package model.command

class KillCommand(private val factory: KillCommandCreator) {
    fun run(pid: Long): Process {
        val command = factory.create(pid)
        return ProcessBuilder(command).start()
    }
}

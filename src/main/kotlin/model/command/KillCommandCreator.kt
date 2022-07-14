package model.command

interface KillCommandCreator {
    fun create(pid: Long): List<String>
}

class KillCommandCreatorForMacOS : KillCommandCreator {
    override fun create(pid: Long): List<String> {
        return buildList {
            add("kill")
            add("-SIGINT")
            add(pid.toString())
        }
    }
}

class KillCommandCreatorForLinux : KillCommandCreator {
    override fun create(pid: Long): List<String> {
        return buildList {
            add("kill")
            add("-SIGINT")
            add(pid.toString())
        }
    }
}

class KillCommandCreatorForWindows : KillCommandCreator {
    override fun create(pid: Long): List<String> {
        return buildList {
            add("taskkill")
            add("/PID")
            add(pid.toString())
        }
    }
}

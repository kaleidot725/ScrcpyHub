package model.repository

class PortRepository {
    private val portList: MutableMap<String, Int> = mutableMapOf()

    fun allocate(key: String): Int? {
        if (portList.any { it.key == key }) {
            return portList[key]
        }

        val emptyPorts = PORTS - portList.values
        if (emptyPorts.isEmpty()) {
            return null
        }

        val allocatePort = emptyPorts.first()
        portList[key] = allocatePort
        return allocatePort
    }

    fun release(key: String) {
        portList.remove(key)
    }

    fun dispose() {
        portList.clear()
    }

    companion object {
        private val TCP_PORT_MIN = 20000
        private val TCP_PORT_MAX = 20016
        private val PORTS: List<Int> = (TCP_PORT_MIN..TCP_PORT_MAX).toList()
    }
}
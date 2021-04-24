package model.repository

import model.entity.Device

class PortRepository {
    private val portList: MutableMap<String, Int> = mutableMapOf()

    fun allocate(device: Device): Int? {
        if (portList.any { it.key == device.id }) {
            return portList[device.id]
        }

        val emptyPorts = PORTS - portList.values
        if (emptyPorts.isEmpty()) {
            return null
        }

        val allocatePort = emptyPorts.first()
        portList[device.id] = allocatePort
        return allocatePort
    }

    fun release(device: Device) {
        portList.remove(device.id)
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
package view.pages.device

import model.entity.Device

interface DevicePageAction {
    fun updateName(name: String)
    fun updateMaxSize(maxSize: String)
    fun updateMaxFrameRate(maxFrameRate: String)
    fun updateBitrate(bitrate: String)
    fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation)
    fun save()
}

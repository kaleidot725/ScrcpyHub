package view.pages.device

import model.entity.Device

interface DevicePageAction {
    fun updateName(name: String)
    fun updateMaxSize(maxSize: String)
    fun updateMaxFrameRate(maxFrameRate: String)
    fun updateBitrate(bitrate: String)
    fun updateBuffering(buffering: String)

    fun updateNoAudio(noAudio: Boolean)
    fun updateAudioBuffering(buffering: String)
    fun updateAudioBitrate(bitrate: String)

    fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation)
    fun updateBorderless(enabled: Boolean)
    fun updateAlwaysOnTop(enabled: Boolean)
    fun updateFullscreen(enabled: Boolean)
    fun updateRotation(rotation: Device.Context.Rotation)
    fun save()
}

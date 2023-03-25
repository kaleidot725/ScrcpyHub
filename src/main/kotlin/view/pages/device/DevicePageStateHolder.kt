package view.pages.device

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.UpdateDeviceSetting
import view.StateHolder

class DevicePageStateHolder(
    private val context: Device.Context,
    private val updateDeviceSetting: UpdateDeviceSetting
) : StateHolder() {
    private val titleName: MutableStateFlow<String> = MutableStateFlow(context.displayName)
    private val editName: MutableStateFlow<String> = MutableStateFlow(context.customName ?: "")

    private val maxSize: MutableStateFlow<String> = MutableStateFlow(context.maxSize?.toString() ?: "")
    private val maxSizeError: MutableStateFlow<String> = MutableStateFlow("")
    private val maxFrameRate: MutableStateFlow<String> = MutableStateFlow(context.maxFrameRate?.toString() ?: "")
    private val maxFrameRateError: MutableStateFlow<String> = MutableStateFlow("")
    private val bitrate: MutableStateFlow<String> = MutableStateFlow(context.bitrate?.toString() ?: "")
    private val bitrateError: MutableStateFlow<String> = MutableStateFlow("")
    private val buffering: MutableStateFlow<String> = MutableStateFlow(context.buffering?.toString() ?: "")
    private val bufferingError: MutableStateFlow<String> = MutableStateFlow("")

    private val noAudio: MutableStateFlow<Boolean> = MutableStateFlow(context.noAudio)
    private val audioBitrate: MutableStateFlow<String> = MutableStateFlow(context.audioBitrate?.toString() ?: "")
    private val audioBitrateError: MutableStateFlow<String> = MutableStateFlow("")
    private val audioBuffering: MutableStateFlow<String> = MutableStateFlow(context.audioBuffering?.toString() ?: "")
    private val audioBufferingError: MutableStateFlow<String> = MutableStateFlow("")

    private val lockOrientation: MutableStateFlow<Device.Context.LockOrientation> = MutableStateFlow(
        Device.Context.LockOrientation.values().firstOrNull { it.value == context.lockOrientation }
            ?: Device.Context.LockOrientation.NONE
    )
    private val enableBorderless: MutableStateFlow<Boolean> = MutableStateFlow(context.enableBorderless)
    private val enableAlwaysOnTop: MutableStateFlow<Boolean> = MutableStateFlow(context.enableAlwaysOnTop)
    private val enableFullscreen: MutableStateFlow<Boolean> = MutableStateFlow(context.enableFullScreen)
    private val rotation: MutableStateFlow<Device.Context.Rotation> = MutableStateFlow(
        Device.Context.Rotation.values().firstOrNull { it.value == context.rotation }
            ?: Device.Context.Rotation.NONE
    )

    val state: StateFlow<DevicePageState> =
        combine(
            titleName,
            editName,
            maxSize,
            maxSizeError,
            maxFrameRate,
            maxFrameRateError,
            bitrate,
            bitrateError,
            buffering,
            bufferingError,
            noAudio,
            audioBuffering,
            audioBufferingError,
            audioBitrate,
            audioBitrateError,
            lockOrientation,
            enableBorderless,
            enableAlwaysOnTop,
            enableFullscreen,
            rotation,
        ) {
            DevicePageState(
                titleName = it[0] as String,
                editName = it[1] as String,
                maxSize = it[2] as String,
                maxSizeError = it[3] as String,
                maxFrameRate = it[4] as String,
                maxFrameRateError = it[5] as String,
                bitrate = it[6] as String,
                bitrateError = it[7] as String,
                buffering = it[8] as String,
                bufferingError = it[9] as String,
                noAudio = it[10] as Boolean,
                audioBuffering = it[11] as String,
                audioBufferingError = it[12] as String,
                audioBitrate = it[13] as String,
                audioBitrateError = it[14] as String,
                lockOrientation = it[15] as Device.Context.LockOrientation,
                enableBorderless = it[16] as Boolean,
                enableAlwaysOnTop = it[17] as Boolean,
                enableFullScreen = it[18] as Boolean,
                rotation = it[19] as Device.Context.Rotation,
                savable = isValid()
            )
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), DevicePageState())

    val viewAction: DevicePageAction = object : DevicePageAction {
        override fun updateName(name: String) {
            coroutineScope.launch {
                editName.emit(name)
            }
        }

        override fun updateMaxSize(maxSize: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.maxSize.emit(maxSize)
            }
        }

        override fun updateMaxFrameRate(maxFrameRate: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.maxFrameRate.emit(maxFrameRate)
            }
        }

        override fun updateBitrate(bitrate: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.bitrate.emit(bitrate)
            }
        }

        override fun updateBuffering(buffering: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.buffering.emit(buffering)
            }
        }

        override fun updateNoAudio(noAudio: Boolean) {
            coroutineScope.launch {
                this@DevicePageStateHolder.noAudio.emit(noAudio)
            }
        }

        override fun updateAudioBitrate(bitrate: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.audioBitrate.emit(bitrate)
            }
        }

        override fun updateAudioBuffering(buffering: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.audioBuffering.emit(buffering)
            }
        }

        override fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation) {
            coroutineScope.launch {
                this@DevicePageStateHolder.lockOrientation.emit(lockOrientation)
            }
        }

        override fun updateBorderless(enabled: Boolean) {
            coroutineScope.launch {
                this@DevicePageStateHolder.enableBorderless.emit(enabled)
            }
        }

        override fun updateAlwaysOnTop(enabled: Boolean) {
            coroutineScope.launch {
                this@DevicePageStateHolder.enableAlwaysOnTop.emit(enabled)
            }
        }

        override fun updateFullscreen(enabled: Boolean) {
            coroutineScope.launch {
                this@DevicePageStateHolder.enableFullscreen.emit(enabled)
            }
        }

        override fun updateRotation(rotation: Device.Context.Rotation) {
            coroutineScope.launch {
                this@DevicePageStateHolder.rotation.emit(rotation)
            }
        }

        override fun save() {
            coroutineScope.launch {
                val newContext = Device.Context(
                    device = context.device,
                    customName = editName.value,
                    maxSize = maxSize.value.toIntOrNull(),
                    maxFrameRate = maxFrameRate.value.toIntOrNull(),
                    bitrate = bitrate.value.toIntOrNull(),
                    buffering = buffering.value.toIntOrNull(),
                    noAudio = noAudio.value,
                    audioBitrate = audioBitrate.value.toIntOrNull(),
                    audioBuffering = audioBuffering.value.toIntOrNull(),
                    lockOrientation = lockOrientation.value.value,
                    enableBorderless = enableBorderless.value,
                    enableAlwaysOnTop = enableAlwaysOnTop.value,
                    enableFullScreen = enableFullscreen.value,
                    rotation = rotation.value.value
                )

                updateDeviceSetting.execute(newContext)
                titleName.value = editName.value
            }
        }
    }

    private suspend fun isValid(): Boolean {
        val maxSizeError = maxSize.value.isNotEmpty() && maxSize.value.toIntOrNull() == null
        val maxSizeErrorMessage = if (maxSizeError) "Please input a number" else ""
        this.maxSizeError.emit(maxSizeErrorMessage)

        val maxFrameRateError = maxFrameRate.value.isNotEmpty() && maxFrameRate.value.toIntOrNull() == null
        val maxFrameRateErrorMessage = if (maxFrameRateError) "Please input a number" else ""
        this.maxFrameRateError.emit(maxFrameRateErrorMessage)

        val bitrateError = bitrate.value.isNotEmpty() && bitrate.value.toIntOrNull() == null
        val bitrateErrorMessage = if (bitrateError) "Please input a number" else ""
        this.bitrateError.emit(bitrateErrorMessage)

        val bufferingError = buffering.value.isNotEmpty() && buffering.value.toIntOrNull() == null
        val bufferingErrorMessage = if (bufferingError) "Please input a number" else ""
        this.bitrateError.emit(bufferingErrorMessage)

        val audioBitrateError = audioBitrate.value.isNotEmpty() && audioBitrate.value.toIntOrNull() == null
        val audioBitrateErrorMessage = if (audioBitrateError) "Please input a number" else ""
        this.audioBitrateError.emit(audioBitrateErrorMessage)

        val audioBufferingError = audioBuffering.value.isNotEmpty() && audioBuffering.value.toIntOrNull() == null
        val audioBufferingErrorMessage = if (audioBufferingError) "Please input a number" else ""
        this.audioBufferingError.emit(audioBufferingErrorMessage)

        val videoError = maxSizeError || maxFrameRateError || bitrateError || bufferingError
        val audioError = audioBitrateError || audioBufferingError
        return !(videoError || audioError)
    }
}

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
    private val updateDeviceSetting: UpdateDeviceSetting,
) : StateHolder() {
    private val titleName: MutableStateFlow<String> = MutableStateFlow(context.displayName)
    private val editName: MutableStateFlow<String> = MutableStateFlow(context.customName ?: "")
    private val enableStayAwake: MutableStateFlow<Boolean> = MutableStateFlow(context.enableStayAwake)
    private val enableShowTouches: MutableStateFlow<Boolean> = MutableStateFlow(context.enableShowTouches)
    private val enablePowerOffOnClose: MutableStateFlow<Boolean> = MutableStateFlow(context.enablePowerOffOnClose)
    private val disablePowerOnOnStart: MutableStateFlow<Boolean> = MutableStateFlow(context.disablePowerOnOnStart)

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

    private val captureOrientation: MutableStateFlow<Device.Context.CaptureOrientation> =
        MutableStateFlow(
            Device.Context.CaptureOrientation.values().firstOrNull { it.value == context.lockOrientation }
                ?: Device.Context.CaptureOrientation.NONE,
        )
    private val enableBorderless: MutableStateFlow<Boolean> = MutableStateFlow(context.enableBorderless)
    private val enableAlwaysOnTop: MutableStateFlow<Boolean> = MutableStateFlow(context.enableAlwaysOnTop)
    private val enableFullscreen: MutableStateFlow<Boolean> = MutableStateFlow(context.enableFullScreen)
    private val enableHidKeyboard: MutableStateFlow<Boolean> = MutableStateFlow(context.enableHidKeyboard)
    private val enableHidMouse: MutableStateFlow<Boolean> = MutableStateFlow(context.enableHidMouse)
    private val orientation: MutableStateFlow<Device.Context.Orientation> =
        MutableStateFlow(
            Device.Context.Orientation.values().firstOrNull { it.value == context.rotation }
                ?: Device.Context.Orientation.NONE,
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
            captureOrientation,
            enableBorderless,
            enableAlwaysOnTop,
            enableFullscreen,
            orientation,
            enableHidKeyboard,
            enableHidMouse,
            enableStayAwake,
            enableShowTouches,
            enablePowerOffOnClose,
            disablePowerOnOnStart,
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
                captureOrientation = it[15] as Device.Context.CaptureOrientation,
                enableBorderless = it[16] as Boolean,
                enableAlwaysOnTop = it[17] as Boolean,
                enableFullScreen = it[18] as Boolean,
                orientation = it[19] as Device.Context.Orientation,
                enableHidKeyboard = it[20] as Boolean,
                enableHidMouse = it[21] as Boolean,
                enableStayAwake = it[22] as Boolean,
                enableShowTouches = it[23] as Boolean,
                enablePowerOffOnClose = it[24] as Boolean,
                disablePowerOnOnStart = it[25] as Boolean,
                savable = isValid(),
            )
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), DevicePageState())

    val viewAction: DevicePageAction =
        object : DevicePageAction {
            override fun updateName(name: String) {
                coroutineScope.launch {
                    editName.emit(name)
                }
            }

            override fun updateEnableStayAwake(enable: Boolean) {
                coroutineScope.launch {
                    enableStayAwake.emit(enable)
                }
            }

            override fun updateEnableShowTouches(enable: Boolean) {
                coroutineScope.launch {
                    enableShowTouches.emit(enable)
                }
            }

            override fun updateEnablePowerOffOnClose(enable: Boolean) {
                coroutineScope.launch {
                    enablePowerOffOnClose.emit(enable)
                }
            }

            override fun updateDisablePowerOnOnStart(disable: Boolean) {
                coroutineScope.launch {
                    disablePowerOnOnStart.emit(disable)
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

            override fun updateLockOrientation(captureOrientation: Device.Context.CaptureOrientation) {
                coroutineScope.launch {
                    this@DevicePageStateHolder.captureOrientation.emit(captureOrientation)
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

            override fun updateRotation(orientation: Device.Context.Orientation) {
                coroutineScope.launch {
                    this@DevicePageStateHolder.orientation.emit(orientation)
                }
            }

            override fun updateEnableHidKeyboard(enabled: Boolean) {
                coroutineScope.launch {
                    this@DevicePageStateHolder.enableHidKeyboard.emit(enabled)
                }
            }

            override fun updateEnableHidMouse(enabled: Boolean) {
                coroutineScope.launch {
                    this@DevicePageStateHolder.enableHidMouse.emit(enabled)
                }
            }

            override fun save() {
                coroutineScope.launch {
                    val newContext =
                        Device.Context(
                            device = context.device,
                            customName = editName.value,
                            maxSize = maxSize.value.toIntOrNull(),
                            maxFrameRate = maxFrameRate.value.toIntOrNull(),
                            bitrate = bitrate.value.toIntOrNull(),
                            buffering = buffering.value.toIntOrNull(),
                            noAudio = noAudio.value,
                            audioBitrate = audioBitrate.value.toIntOrNull(),
                            audioBuffering = audioBuffering.value.toIntOrNull(),
                            lockOrientation = captureOrientation.value.value,
                            enableBorderless = enableBorderless.value,
                            enableAlwaysOnTop = enableAlwaysOnTop.value,
                            enableFullScreen = enableFullscreen.value,
                            rotation = orientation.value.value,
                            enableHidKeyboard = enableHidKeyboard.value,
                            enableHidMouse = enableHidMouse.value,
                            enableStayAwake = enableStayAwake.value,
                            enableShowTouches = enableShowTouches.value,
                            enablePowerOffOnClose = enablePowerOffOnClose.value,
                            disablePowerOnOnStart = disablePowerOnOnStart.value,
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

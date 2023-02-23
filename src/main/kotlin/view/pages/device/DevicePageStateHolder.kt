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
    private val lockOrientation: MutableStateFlow<Device.Context.LockOrientation> = MutableStateFlow(
        Device.Context.LockOrientation.values().firstOrNull { it.value == context.lockOrientation }
            ?: Device.Context.LockOrientation.NONE
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
            lockOrientation
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
                lockOrientation = it[8] as Device.Context.LockOrientation,
                savable = isValid()
            )
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), DevicePageState())

    val viewAction: DevicePageAction = object : DevicePageAction {
        override fun updateName(name: String) {
            coroutineScope.launch {
                editName.emit(name)
                isValid()
            }
        }

        override fun updateMaxSize(maxSize: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.maxSize.emit(maxSize)
                isValid()
            }
        }

        override fun updateMaxFrameRate(maxFrameRate: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.maxFrameRate.emit(maxFrameRate)
                isValid()
            }
        }

        override fun updateBitrate(bitrate: String) {
            coroutineScope.launch {
                this@DevicePageStateHolder.bitrate.emit(bitrate)
                isValid()
            }
        }

        override fun updateLockOrientation(lockOrientation: Device.Context.LockOrientation) {
            coroutineScope.launch {
                this@DevicePageStateHolder.lockOrientation.emit(lockOrientation)
                isValid()
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
                    lockOrientation = lockOrientation.value.value,
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

        return !(maxSizeError || maxFrameRateError || bitrateError)
    }
}

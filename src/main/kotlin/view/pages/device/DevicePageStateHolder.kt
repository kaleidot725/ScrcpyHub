package view.pages.device

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.UpdateDeviceSetting
import view.StateHolder

class DevicePageStateHolder(
    private val context: Device.Context,
    private val updateDeviceSetting: UpdateDeviceSetting
) : StateHolder() {
    private val _titleName: MutableStateFlow<String> = MutableStateFlow(context.displayName)
    val titleName: StateFlow<String> = _titleName

    private val _editName: MutableStateFlow<String> = MutableStateFlow(context.customName ?: "")
    val editName: StateFlow<String> = _editName

    private val _maxSize: MutableStateFlow<String> = MutableStateFlow(context.maxSize?.toString() ?: "")
    val maxSize: StateFlow<String> = _maxSize

    private val _maxSizeError: MutableStateFlow<String> = MutableStateFlow("")
    val maxSizeError: StateFlow<String> = _maxSizeError

    private val _maxFrameRate: MutableStateFlow<String> = MutableStateFlow(context.maxFrameRate?.toString() ?: "")
    val maxFrameRate: StateFlow<String> = _maxFrameRate

    private val _maxFrameRateError: MutableStateFlow<String> = MutableStateFlow("")
    val maxFrameRateError: StateFlow<String> = _maxFrameRateError

    private val _bitrate: MutableStateFlow<String> = MutableStateFlow(context.bitrate?.toString() ?: "")
    val bitrate: StateFlow<String> = _bitrate

    private val _bitrateError: MutableStateFlow<String> = MutableStateFlow("")
    val bitrateError: StateFlow<String> = _bitrateError

    private val _savable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val savable: StateFlow<Boolean> = _savable

    init {
        coroutineScope.launch {
            validate()
        }
    }

    fun updateName(name: String) {
        coroutineScope.launch {
            _editName.emit(name)
            validate()
        }
    }

    fun updateMaxSize(maxSize: String) {
        coroutineScope.launch {
            _maxSize.emit(maxSize)
            validate()
        }
    }

    fun updateMaxFrameRate(maxFrameRate: String) {
        coroutineScope.launch {
            _maxFrameRate.emit(maxFrameRate)
            validate()
        }
    }

    fun updateBitrate(bitrate: String) {
        coroutineScope.launch {
            _bitrate.emit(bitrate)
            validate()
        }
    }

    fun save() {
        coroutineScope.launch {
            val newContext = Device.Context(
                device = context.device,
                customName = _editName.value,
                maxSize = _maxSize.value.toIntOrNull(),
                maxFrameRate = _maxFrameRate.value.toIntOrNull(),
                bitrate = _bitrate.value.toIntOrNull()
            )

            updateDeviceSetting.execute(newContext)
            _titleName.value = _editName.value
        }
    }

    private suspend fun validate() {
        val maxSizeError = _maxSize.value.isNotEmpty() && _maxSize.value.toIntOrNull() == null
        val maxSizeErrorMessage = if (maxSizeError) "Please input a number" else ""
        _maxSizeError.emit(maxSizeErrorMessage)

        val maxFrameRateError = _maxFrameRate.value.isNotEmpty() && _maxFrameRate.value.toIntOrNull() == null
        val maxFrameRateErrorMessage = if (maxFrameRateError) "Please input a number" else ""
        _maxFrameRateError.emit(maxFrameRateErrorMessage)

        val bitrateError = _bitrate.value.isNotEmpty() && _bitrate.value.toIntOrNull() == null
        val bitrateErrorMessage = if (bitrateError) "Please input a number" else ""
        _bitrateError.emit(bitrateErrorMessage)

        val hasError = maxSizeError || maxFrameRateError || bitrateError
        _savable.emit(!hasError)
    }
}

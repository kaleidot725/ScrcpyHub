package model.di

import common.OsType
import common.osType
import model.entity.Device
import model.repository.DeviceRepository
import model.repository.MessageRepository
import model.repository.ProcessRepository
import model.repository.SettingRepository
import model.usecase.FetchDevicesUseCase
import model.usecase.FetchSettingUseCase
import model.usecase.GetDevicesFlowUseCase
import model.usecase.GetMessageFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.IsSetupCompletedUseCase
import model.usecase.SaveScreenshotToDesktopUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import model.usecase.UpdateDeviceNameUseCase
import model.usecase.UpdateSettingUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import viewmodel.DevicePageViewModel
import viewmodel.DevicesPageViewModel
import viewmodel.MainContentViewModel
import viewmodel.SettingPageViewModel

val appModule = module {
    single(named("setting_directory")) {
        when (osType()) {
            OsType.MAC_OS -> "/Library/Application Support/ScrcpyHub/"
            OsType.WINDOWS -> "./"
        }
    }

    single {
        MessageRepository()
    }

    single {
        ProcessRepository()
    }

    factory {
        val directory = get<String>(named("setting_directory"))
        DeviceRepository(directory)
    }

    factory {
        val directory = get<String>(named("setting_directory"))
        SettingRepository(directory)
    }

    factory {
        FetchDevicesUseCase(get())
    }

    factory {
        GetDevicesFlowUseCase(get())
    }

    factory {
        FetchSettingUseCase(get())
    }

    factory {
        GetScrcpyStatusUseCase(get())
    }

    factory {
        StartScrcpyUseCase(get(), get())
    }

    factory {
        SaveScreenshotToDesktopUseCase(get(), get())
    }

    factory {
        StopScrcpyUseCase(get())
    }

    factory {
        IsSetupCompletedUseCase(get())
    }

    factory {
        UpdateSettingUseCase(get())
    }

    factory {
        UpdateDeviceNameUseCase(get())
    }

    factory {
        GetMessageFlowUseCase(get())
    }

    factory {
        StartScrcpyRecordUseCase(get(), get(), get())
    }

    factory {
        DevicesPageViewModel(get(), get(), get(), get(), get(), get(), get())
    }

    factory { (context: Device.Context) ->
        DevicePageViewModel(context, get())
    }

    factory {
        MainContentViewModel(get(), get())
    }

    factory {
        SettingPageViewModel(get(), get())
    }
}

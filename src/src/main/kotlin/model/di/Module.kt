package model.di

import model.entity.Device
import model.repository.DeviceRepository
import model.repository.ProcessRepository
import model.repository.SettingRepository
import model.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import viewmodel.DevicePageViewModel
import viewmodel.DevicesPageViewModel
import viewmodel.MainContentViewModel
import viewmodel.SettingPageViewModel

val appModule = module {
    single(named("setting_directory")) {
        when (System.getProperty("os.name")) {
            "Mac OS X" -> "/Library/Application Support/ScrcpyHub/"
            else -> "./"
        }
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
        IsScrcpyRunningUseCase(get())
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
        DevicesPageViewModel(get(), get(), get(), get(), get(), get())
    }

    factory { (device: Device) ->
        DevicePageViewModel(device, get())
    }

    factory {
        MainContentViewModel(get())
    }

    factory {
        SettingPageViewModel(get(), get())
    }
}
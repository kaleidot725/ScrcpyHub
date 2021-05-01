package model.di

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.*
import model.usecase.*
import org.koin.dsl.module

val appModule = module {
    single {
        val settingRepository = SettingRepository()
        val setting = settingRepository.get()
        AdbCommand(path = setting.adbLocation)
    }

    single {
        val settingRepository = SettingRepository()
        val setting = settingRepository.get()
        ScrcpyCommand(path = setting.scrcpyLocation)
    }

    single {
        PortRepository()
    }

    single {
        ProcessRepository()
    }

    single {
        DeviceRepository(get())
    }

    single {
        ResolutionRepository()
    }

    single {
        SettingRepository()
    }

    single {
        FetchDevicesUseCase(get())
    }

    single {
        FetchResolutionsUseCase(get())
    }

    single {
        FetchSettingUseCase(get())
    }

    single {
        IsScrcpyRunningUseCase(get())
    }

    single {
        StartScrcpyUseCase(get(), get(), get())
    }

    single {
        StopScrcpyUseCase(get(), get())
    }

    single {
        IsSetupCompletedUseCase(get(), get())
    }

    single {
        UpdateSettingUseCase(get(), get(), get())
    }
}
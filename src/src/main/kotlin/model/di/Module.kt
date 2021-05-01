package model.di

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.*
import model.usecase.*
import org.koin.dsl.module

val appModule = module {
    single {
        AdbCommand()
    }

    single {
        ScrcpyCommand()
    }

    single {
        DeviceRepository(get())
    }

    single {
        PortRepository()
    }

    single {
        ProcessRepository()
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
        UpdateSettingUseCase(get())
    }
}
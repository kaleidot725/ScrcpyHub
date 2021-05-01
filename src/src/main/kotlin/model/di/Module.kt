package model.di

import model.command.AdbCommand
import model.command.ScrcpyCommand
import model.repository.DeviceRepository
import model.repository.PortRepository
import model.repository.ProcessRepository
import model.repository.ResolutionRepository
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
        FetchDevicesUseCase(get())
    }

    single {
        FetchResolutionsUseCase(get())
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
}
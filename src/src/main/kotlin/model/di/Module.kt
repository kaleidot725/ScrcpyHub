package model.di

import model.command.AdbCommand
import model.command.ScrcpyCommand
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
        val settingRepository = get<SettingRepository>()
        val setting = settingRepository.get()
        AdbCommand(adbPath = setting.adbLocation ?: "")
    }

    single {
        val settingRepository = get<SettingRepository>()
        val setting = settingRepository.get()
        ScrcpyCommand(adbPath = setting.adbLocation ?: "", scrcpyPath = setting.scrcpyLocation ?: "")
    }

    single {
        ProcessRepository()
    }

    single {
        DeviceRepository(get())
    }

    single {
        val directory = get<String>(named("setting_directory"))
        SettingRepository(directory)
    }

    single {
        FetchDevicesUseCase(get())
    }

    single {
        FetchSettingUseCase(get())
    }

    single {
        IsScrcpyRunningUseCase(get())
    }

    single {
        StartScrcpyUseCase(get(), get())
    }

    single {
        StopScrcpyUseCase(get())
    }

    single {
        IsSetupCompletedUseCase(get(), get())
    }

    single {
        UpdateSettingUseCase(get(), get(), get())
    }

    factory {
        DevicesPageViewModel(get(), get(), get(), get())
    }

    factory {
        DevicePageViewModel()
    }

    factory {
        MainContentViewModel(get())
    }

    factory {
        SettingPageViewModel(get(), get())
    }
}
package model.di

import kotlinx.coroutines.runBlocking
import model.command.KillCommand
import model.command.ScrcpyCommand
import model.command.creator.KillCommandCreatorForLinux
import model.command.creator.KillCommandCreatorForMacOS
import model.command.creator.KillCommandCreatorForWindows
import model.command.creator.ScrcpyCommandCreator
import model.entity.Device
import model.os.OSContext
import model.os.OSType
import model.os.factory.OSContextFactory
import model.repository.DeviceRepository
import model.repository.MessageRepository
import model.repository.ProcessRepository
import model.repository.SettingRepository
import model.usecase.FetchDevicesUseCase
import model.usecase.FetchSettingUseCase
import model.usecase.GetDevicesFlowUseCase
import model.usecase.GetMessageFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import model.usecase.IsSetupCompletedUseCase
import model.usecase.SaveScreenshotToDesktopUseCase
import model.usecase.StartAdbServerUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyRecordUseCase
import model.usecase.StopScrcpyUseCase
import model.usecase.UpdateDeviceSetting
import model.usecase.UpdateSettingUseCase
import org.koin.dsl.module
import viewmodel.DevicePageViewModel
import viewmodel.DevicesPageViewModel
import viewmodel.MainContentViewModel
import viewmodel.SettingPageViewModel

val appModule = module {
    single {
        MessageRepository()
    }

    factory {
        OSContextFactory.create()
    }

    factory {
        KillCommand(
            when (get<OSContext>().type) {
                OSType.MAC_OS -> KillCommandCreatorForMacOS()
                OSType.LINUX -> KillCommandCreatorForLinux()
                OSType.WINDOWS -> KillCommandCreatorForWindows()
            }
        )
    }

    factory {
        val setting = runBlocking { get<SettingRepository>().get() }
        ScrcpyCommand(ScrcpyCommandCreator(setting.scrcpyLocation))
    }

    factory {
        ProcessRepository(get(), get())
    }

    factory {
        DeviceRepository(get())
    }

    factory {
        SettingRepository(get())
    }

    factory {
        FetchDevicesUseCase(get())
    }

    factory {
        GetDevicesFlowUseCase(get())
    }

    factory {
        StartAdbServerUseCase(get())
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
        UpdateDeviceSetting(get())
    }

    factory {
        GetMessageFlowUseCase(get())
    }

    factory {
        StartScrcpyRecordUseCase(get(), get(), get(), get())
    }

    factory {
        StopScrcpyRecordUseCase(get(), get())
    }

    factory {
        GetSystemDarkModeFlowUseCase()
    }

    factory {
        DevicesPageViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get())
    }

    factory { (context: Device.Context) ->
        DevicePageViewModel(context, get())
    }

    factory {
        MainContentViewModel(get(), get(), get(), get())
    }

    factory {
        SettingPageViewModel(get(), get())
    }
}

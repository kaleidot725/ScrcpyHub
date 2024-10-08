package model.di

import kotlinx.coroutines.runBlocking
import model.command.KillCommand
import model.command.KillCommandCreatorForLinux
import model.command.KillCommandCreatorForMacOS
import model.command.KillCommandCreatorForWindows
import model.command.ScrcpyCommand
import model.command.ScrcpyCommandCreator
import model.entity.Device
import model.os.OSContext
import model.os.OSContextForLinux
import model.os.OSContextForMac
import model.os.OSContextForWindows
import model.os.OSType
import model.os.getOSType
import model.repository.DeviceRepository
import model.repository.MessageRepository
import model.repository.ProcessRepository
import model.repository.SettingRepository
import model.usecase.CheckSetupStatusUseCase
import model.usecase.FetchDevicesUseCase
import model.usecase.FetchSettingUseCase
import model.usecase.GetErrorMessageFlowUseCase
import model.usecase.GetNotifyMessageFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.GetSystemDarkModeFlowUseCase
import model.usecase.RestartAdbServerUseCase
import model.usecase.SaveScreenshotUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyRecordUseCase
import model.usecase.StopScrcpyUseCase
import model.usecase.UpdateDeviceSetting
import model.usecase.UpdateSettingUseCase
import org.koin.dsl.module
import view.MainContentStateHolder
import view.pages.device.DevicePageStateHolder
import view.pages.devices.DevicesPageStateHolder
import view.pages.setting.SettingPageStateHolder

val appModule =
    module {
        single {
            MessageRepository()
        }

        factory {
            when (getOSType()) {
                OSType.MAC_OS -> OSContextForMac()
                OSType.LINUX -> OSContextForLinux()
                OSType.WINDOWS -> OSContextForWindows()
            }
        }

        factory {
            KillCommand(
                when (get<OSContext>().type) {
                    OSType.MAC_OS -> KillCommandCreatorForMacOS()
                    OSType.LINUX -> KillCommandCreatorForLinux()
                    OSType.WINDOWS -> KillCommandCreatorForWindows()
                },
            )
        }

        factory {
            val setting = runBlocking { get<SettingRepository>().get() }
            ScrcpyCommand(ScrcpyCommandCreator(setting.scrcpyLocation))
        }

        factory {
            ProcessRepository(get())
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
            RestartAdbServerUseCase(get())
        }

        factory {
            FetchSettingUseCase(get())
        }

        factory {
            GetScrcpyStatusUseCase(get())
        }

        factory {
            StartScrcpyUseCase(get(), get(), get())
        }

        factory {
            SaveScreenshotUseCase(get(), get(), get())
        }

        factory {
            StopScrcpyUseCase(get(), get())
        }

        factory {
            CheckSetupStatusUseCase(get(), get())
        }

        factory {
            UpdateSettingUseCase(get())
        }

        factory {
            UpdateDeviceSetting(get())
        }

        factory {
            GetNotifyMessageFlowUseCase(get())
        }

        factory {
            StartScrcpyRecordUseCase(get(), get(), get())
        }

        factory {
            StopScrcpyRecordUseCase(get(), get())
        }

        factory {
            GetSystemDarkModeFlowUseCase()
        }

        factory {
            GetErrorMessageFlowUseCase(get())
        }

        factory {
            DevicesPageStateHolder(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }

        factory { (context: Device.Context) ->
            DevicePageStateHolder(context, get())
        }

        factory {
            MainContentStateHolder(get(), get(), get(), get())
        }

        factory {
            SettingPageStateHolder(get(), get())
        }
    }

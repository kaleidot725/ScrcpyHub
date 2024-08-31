pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven("https://maven.hq.hydraulic.software")
    }
    plugins {
        kotlin("jvm").version("2.0.20")
        kotlin("plugin.serialization").version("2.0.20")
        id("org.jetbrains.kotlin.plugin.compose").version("2.0.20")
        id("org.jetbrains.compose").version("1.6.11")
        id("org.jlleitschuh.gradle.ktlint").version("12.1.1")
        id("com.mikepenz.aboutlibraries.plugin").version("11.2.3")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            library("turtle", "com.lordcodes.turtle:turtle:0.10.0")
            library("koin", "io.insert-koin:koin-core:3.5.6")
            library("kotlin-serialization", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
            library("adam", "com.malinskiy.adam:adam:0.5.8")
            library("jSystemThemeDetector", "com.github.Dansoftowner:jSystemThemeDetector:3.6")
            library("junit", "org.junit.jupiter:junit-jupiter:5.9.0")
            library("mockk", "io.mockk:mockk:1.13.2")
            library("kotest", "io.kotest:kotest-runner-junit5:5.5.4")
            library("aboutlibraries-core", "com.mikepenz:aboutlibraries-core:11.2.3")
            library("aboutlibraries-compose", "com.mikepenz:aboutlibraries-compose:11.2.3")
        }
    }
}

rootProject.name = "ScrcpyHub"

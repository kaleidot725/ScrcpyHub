pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven("https://maven.hq.hydraulic.software")
    }
    plugins {
        kotlin("jvm").version("1.7.20")
        kotlin("plugin.serialization").version("1.7.20")
        id("org.jetbrains.compose").version("1.3.1")
        id("org.jlleitschuh.gradle.ktlint").version("11.0.0")
        id("com.mikepenz.aboutlibraries.plugin").version("10.6.1")
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
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            library("turtle", "com.lordcodes.turtle:turtle:0.8.0")
            library("koin", "io.insert-koin:koin-core:3.2.2")
            library("kotlin-serialization", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            library("adam", "com.malinskiy.adam:adam:0.4.8")
            library("jSystemThemeDetector", "com.github.Dansoftowner:jSystemThemeDetector:3.6")
            library("junit", "org.junit.jupiter:junit-jupiter:5.9.0")
            library("mockk", "io.mockk:mockk:1.13.2")
            library("kotest", "io.kotest:kotest-runner-junit5:5.5.4")
            library("aboutlibraries-core", "com.mikepenz:aboutlibraries-core:10.6.1")
            library("aboutlibraries-compose", "com.mikepenz:aboutlibraries-compose:10.6.1")
        }
    }
}
rootProject.name = "ScrcpyHub"

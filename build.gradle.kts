import org.jetbrains.compose.desktop.application.dsl.TargetFormat

group = "jp.kaleidot725"
version = "2.2.0"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.mikepenz.aboutlibraries.plugin")
}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material)
    implementation(compose.materialIconsExtended)
    implementation(libs.kotlin.coroutines)
    implementation(libs.turtle)
    implementation(libs.koin)
    implementation(libs.kotlin.serialization)
    implementation(libs.adam)
    implementation(libs.jSystemThemeDetector)
    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose)
    implementation(libs.napier)
    implementation("io.github.vinceglb:filekit-core:0.8.2")
    implementation("io.github.vinceglb:filekit-compose:0.8.2")
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest)
}

tasks.test {
    useJUnitPlatform()
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

kotlin {
    jvmToolchain(jdkVersion = 17)
}

compose.desktop {
    application {
        mainClass = "ScrcpyHubKt"
        nativeDistributions {
            packageName = "ScrcpyHub"
            modules("jdk.management")

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            macOS {
                bundleID = "jp.kaleidot725.scrcpyhub"
                iconFile.set(project.file("icon.icns"))
                entitlementsFile.set(project.file("default.entitlements"))
            }

            windows {
                iconFile.set(project.file("icon.ico"))
            }

            linux {
                iconFile.set(project.file("icon.ico"))
            }
        }
    }
}

aboutLibraries {
    registerAndroidTasks = false
    prettyPrint = true
}

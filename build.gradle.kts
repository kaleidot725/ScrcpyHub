group = "jp.kaleidot725"
version = "1.8.0"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("org.jlleitschuh.gradle.ktlint")
    id("dev.hydraulic.conveyor")
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
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest)
}

compose.desktop {
    application {
        mainClass = "ScrcpyHubKt"
        nativeDistributions {
            modules("jdk.management")
            macOS {
                bundleID = "jp.kaleidot725.scrcpyhub"
                signing {
                    sign.set(false)
                }
            }
            windows {

            }
        }
    }
}

dependencies {
    linuxAmd64(compose.desktop.linux_x64)
    macAmd64(compose.desktop.macos_x64)
    macAarch64(compose.desktop.macos_arm64)
    windowsAmd64(compose.desktop.windows_x64)
}

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}

// Force override the Kotlin stdlib version used by Compose to 1.7, as otherwise we can end up with a mix of 1.6 and 1.7 on our classpath.
dependencies {
    val v = "1.7.20"
    for (m in setOf("linuxAmd64", "macAmd64", "macAarch64", "windowsAmd64")) {
        m("org.jetbrains.kotlin:kotlin-stdlib:$v")
        m("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$v")
        m("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$v")
    }
}
// endregion

tasks.test {
    useJUnitPlatform()
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
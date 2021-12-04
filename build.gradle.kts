import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
    kotlin("plugin.serialization") version "1.4.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

group = "me.kaleidot725"
version = "1.2.3"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.lordcodes.turtle:turtle:0.6.0")
    implementation("io.insert-koin:koin-core:3.1.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation("com.malinskiy.adam:adam:0.4.3")

    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-runner-junit5:5.0.1")
    testImplementation("io.mockk:mockk:1.12.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}

compose.desktop {
    application {
        mainClass = "ScrcpyHubKt"
        nativeDistributions {
            packageName = "ScrcpyHub"
            modules("jdk.management")

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi)

            macOS {
                bundleID = "jp.kaleidot725.scrcpyhub"
                iconFile.set(project.file("icon.icns"))
            }

            windows {
                iconFile.set(project.file("icon.ico"))
            }
        }
    }
}

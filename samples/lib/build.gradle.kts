import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict

    android {
        namespace = "io.github.rribeiro5.samples.lib"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget = JvmTarget.JVM_17 }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            // Primary artifact (using AndroidX App Startup)
            implementation(project(":kmp-context-provider"))

            // Dependency-free alternative (uncomment to switch and comment out above):
            // implementation(project(":kmp-context-provider-no-deps"))
        }
    }
}

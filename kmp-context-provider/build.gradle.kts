import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
    alias(libs.plugins.dokka)
}

dokka {
    moduleName.set("kmp-context-provider")
    moduleVersion.set(libs.versions.lib.version.get())
    dokkaSourceSets.configureEach {
        includes.from(file("MODULE.md"))
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    baseline = file("detekt-baseline.xml")
    source.setFrom(files("src"))
}

kotlin {
    explicitApi = ExplicitApiMode.Strict

    android {
        namespace = "io.github.rribeiro5.context"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        androidResources.enable = true
        compilerOptions { jvmTarget = JvmTarget.JVM_17 }
        withHostTest {}
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }


    sourceSets {
        androidMain.dependencies {
            implementation(libs.startup)
        }
        getByName("androidHostTest").dependencies {
            implementation(kotlin("test"))
            implementation(libs.robolectric)
            implementation(libs.mockk)
            implementation(libs.androidx.test.core)
        }
        getByName("androidDeviceTest").dependencies {
            implementation(kotlin("test"))
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.test.runner)
        }
    }
}

//Publishing your Kotlin Multiplatform library to Maven Central
//https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.rribeiro5", "kmp-context-provider", libs.versions.lib.version.get())

    pom {
        name = "kmp-context-provider"
        description = "Lightweight library that automatically provides Android Context across Kotlin Multiplatform and Android projects without manual context passing"
        url = "https://rribeiro5.github.io/kmp-context-provider/"

        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        developers {
            developer {
                id = "rribeiro5"
                name = "Rafael Ribeiro"
                email = "rribeiro5.dev@proton.me"
            }
        }

        scm {
            url = "https://github.com/rribeiro5/kmp-context-provider"
        }
    }
}

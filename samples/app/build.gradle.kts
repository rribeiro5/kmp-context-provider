plugins {
    alias(libs.plugins.android.app)
}

android {
    namespace = "io.github.rribeiro5.samples.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.rribeiro5.samples.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":samples:lib"))
    implementation(project(":kmp-context-provider"))

    implementation(libs.sample.androidx.appcompat)
    implementation(libs.sample.androidx.activity)
}

plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.android.kmp.library).apply(false)
    alias(libs.plugins.maven.publish).apply(false)
    alias(libs.plugins.detekt).apply(false)
    alias(libs.plugins.kover).apply(false)
    alias(libs.plugins.dokka)
    // Samples
    alias(libs.plugins.android.app).apply(false)
}

dokka {
    moduleName.set("kmp-context-provider")
    moduleVersion.set(libs.versions.lib.version.get())
    dokkaPublications.configureEach {
        includes.from(file("MODULE.md"))
    }
}

dependencies {
    dokka(project(":kmp-context-provider"))
    dokka(project(":kmp-context-provider-no-deps"))
}

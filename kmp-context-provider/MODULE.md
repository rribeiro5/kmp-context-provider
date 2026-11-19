# Module kmp-context-provider

Main artifact of `kmp-context-provider`, automatically initialized at application launch using **AndroidX App Startup** (`androidx.startup:startup-runtime`).

## Gradle Setup

### Version Catalog (`gradle/libs.versions.toml`)
```toml
[versions]
kmp-context-provider = "<latest-version>"

[libraries]
kmp-context-provider = { module = "io.github.rribeiro5:kmp-context-provider", version.ref = "kmp-context-provider" }
```

### Kotlin Multiplatform (KMP) Module
```kotlin
kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.kmp.context.provider)
            // Or without version catalog:
            // implementation("io.github.rribeiro5:kmp-context-provider:<latest-version>")
        }
    }
}
```

### Android-Only Module
```kotlin
dependencies {
    implementation(libs.kmp.context.provider)
    // Or without version catalog:
    // implementation("io.github.rribeiro5:kmp-context-provider:<latest-version>")
}
```

## Usage Examples

```kotlin
import io.github.rribeiro5.context.ContextProvider

// Get Application Context
val appContext = ContextProvider.applicationContext

// Get Application Instance
val app = ContextProvider.application

// Access current resumed Activity (safe nullable call)
val currentActivity = ContextProvider.activity

// Access current Activity as a Context alias
val activityContext = ContextProvider.activityContext
```

## How It Works

1. **AndroidX App Startup:** At application launch, `ApplicationContextInitializer` automatically initializes `ContextProvider` with the application context.
2. **Lifecycle Monitoring:** Registers `Application.ActivityLifecycleCallbacks` to track active activities.
3. **Memory Safety:** Holds current `Activity` references via `WeakReference` to eliminate activity memory leaks.

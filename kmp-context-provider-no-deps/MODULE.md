# Module kmp-context-provider-no-deps

Standalone zero-dependency artifact of `kmp-context-provider`, automatically initialized at application launch using an Android **`ContentProvider`**. Ideal for projects that do not use AndroidX App Startup.

## Gradle Setup

### Version Catalog (`gradle/libs.versions.toml`)
```toml
[versions]
kmp-context-provider = "<latest-version>"

[libraries]
kmp-context-provider-no-deps = { module = "io.github.rribeiro5:kmp-context-provider-no-deps", version.ref = "kmp-context-provider" }
```

### Kotlin Multiplatform (KMP) Module
```kotlin
kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.kmp.context.provider.no.deps)
            // Or without version catalog:
            // implementation("io.github.rribeiro5:kmp-context-provider-no-deps:<latest-version>")
        }
    }
}
```

### Android-Only Module
```kotlin
dependencies {
    implementation("io.github.rribeiro5:kmp-context-provider-no-deps:<latest-version>")
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

## Trade-offs & Cons

- **ContentProvider Overhead:** Initializes via a dedicated `<provider>` entry in `AndroidManifest.xml`, incurring minor OS process initialization overhead compared to consolidated AndroidX App Startup initializers.
- **No Explicit Dependency Ordering:** Does not support explicit initialization dependency ordering (unlike `androidx.startup.Initializer.dependencies()`).
- **Manifest Entry:** Merges an additional `<provider>` tag into your compiled `AndroidManifest.xml`.

## How It Works

1. **ContentProvider Initialization:** At application launch, `ApplicationContentProvider` automatically initializes `ContextProvider` with the application context before `Application.onCreate()`.
2. **Lifecycle Monitoring:** Registers `Application.ActivityLifecycleCallbacks` to track active activities.
3. **Memory Safety:** Holds current `Activity` references via `WeakReference` to eliminate activity memory leaks.

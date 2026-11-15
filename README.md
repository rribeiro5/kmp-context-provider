# kmp-context-provider

[![CI](https://img.shields.io/github/actions/workflow/status/rribeiro5/kmp-context-provider/ci.yml?branch=main&label=CI)](https://github.com/rribeiro5/kmp-context-provider/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.rribeiro5/kmp-context-provider)](https://central.sonatype.com/artifact/io.github.rribeiro5/kmp-context-provider)
[![API Reference](https://img.shields.io/badge/API_Reference-Dokka-blue)](https://rribeiro5.github.io/kmp-context-provider/)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![License](https://img.shields.io/github/license/rribeiro5/kmp-context-provider)](LICENSE)

A lightweight library that automatically provides Android `Context`, `Application`, and `Activity` references across Kotlin Multiplatform (KMP) and Android projects without manual context passing.

```kotlin
import io.github.rribeiro5.context.ContextProvider

// Retrieve application context anywhere
val context = ContextProvider.applicationContext

// Retrieve active Activity (if currently resumed)
val activity = ContextProvider.activity
```

## Quickstart Guide

### 1. Add Dependency

Add the main artifact to your module's `build.gradle.kts`:

#### Version Catalog (`gradle/libs.versions.toml`)
```toml
[versions]
kmp-context-provider = "<latest-version>"

[libraries]
kmp-context-provider = { module = "io.github.rribeiro5:kmp-context-provider", version.ref = "kmp-context-provider" }
```

#### Gradle (`build.gradle.kts`)
```kotlin
dependencies {
    implementation(libs.kmp.context.provider)
    // Or without version catalog:
    // implementation("io.github.rribeiro5:kmp-context-provider:<latest-version>")
}
```

### 2. Usage Examples

No manual initialization call is required in `Application.onCreate()`. Use `ContextProvider` directly anywhere in your code:

```kotlin
import io.github.rribeiro5.context.ContextProvider

// Get Application Context
val appContext = ContextProvider.applicationContext

// Get Application Instance
val app = ContextProvider.application

// Access current resumed Activity (safe nullable call)
val currentActivity = ContextProvider.activity
currentActivity?.let { activity ->
    // Perform activity-dependent operations
}

// Access current Activity as a Context alias
val activityContext = ContextProvider.activityContext
```

#### Java Interoperability

```java
import io.github.rribeiro5.context.ContextProvider;
import android.content.Context;
import android.app.Activity;

Context context = ContextProvider.getApplicationContext();
Activity activity = ContextProvider.getActivity();
```

## Public API Reference

The primary entry point is the `ContextProvider` singleton object:

| Property | Type | Description |
| :--- | :--- | :--- |
| `applicationContext` | `Context` | Global application context. Throws `IllegalStateException` if accessed before initialization. |
| `application` | `Application` | The global `Application` instance. |
| `activity` | `Activity?` | The currently active (resumed) `Activity`, or `null` if no activity is resumed. |
| `activityContext` | `Context?` | The currently active (resumed) `Activity` typed as a `Context` (alias for `activity`). |

For complete API details, check out the full [API Reference](https://rribeiro5.github.io/kmp-context-provider/).

## Dependency-Free Alternative: `kmp-context-provider-no-deps`

If your project does not use AndroidX App Startup (`androidx.startup:startup-runtime`) and you want zero transitive dependencies, use the standalone `no-deps` artifact.

> [!WARNING]
> Do not include both `kmp-context-provider` and `kmp-context-provider-no-deps` in the same project. Use only one artifact depending on whether your project uses AndroidX App Startup.

### Gradle Setup

```kotlin
dependencies {
    implementation("io.github.rribeiro5:kmp-context-provider-no-deps:<latest-version>")
}
```

### Trade-offs / Cons of `no-deps`

- **ContentProvider Startup Overhead:** Initializes via a dedicated `<provider>` declared in `AndroidManifest.xml`. Each `ContentProvider` registered in an app incurs minor OS process initialization overhead compared to consolidated AndroidX App Startup initializers.
- **No Explicit Dependency Ordering:** Does not support explicit initialization dependency ordering (unlike `androidx.startup.Initializer.dependencies()`).
- **Manifest Entry:** Merges an additional `<provider>` entry into your app's compiled `AndroidManifest.xml`.

## How It Works

1. **Automatic Initialization:** At application launch, `ContextProvider` is automatically initialized using **AndroidX App Startup** (in the main `kmp-context-provider` artifact) or a **`ContentProvider`** (in `kmp-context-provider-no-deps`).
2. **Lifecycle Monitoring:** Upon initialization, it registers `Application.ActivityLifecycleCallbacks` on the application instance.
3. **Memory-Safe Activity Tracking:** It updates the reference to the active `Activity` during `onActivityResumed` and clears it during `onActivityPaused`. The reference is stored using a `WeakReference` to eliminate any risk of activity memory leaks.

## License

This project is licensed under the [MIT License](LICENSE).

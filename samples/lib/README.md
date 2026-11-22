# Sample KMP Library (`:samples:lib`)

This subproject is a Kotlin Multiplatform library demonstrating how to expose cross-platform APIs using `expect`/`actual` declarations where the Android actuals rely on `ContextProvider`.

## API Demonstrations

Each class in this sample library targets one of the four core `ContextProvider` properties:

| Sample Class | `ContextProvider` Property | Description |
| :--- | :--- | :--- |
| `KeyValueStorage` | `applicationContext` | Accesses global `SharedPreferences` on Android (`NSUserDefaults` on iOS). |
| `MemoryWatcher` | `application` | Registers `ComponentCallbacks2` on the `Application` instance for low memory warnings (`NSNotificationCenter` on iOS). |
| `DialogPresenter` | `activityContext` | Displays native alert dialogs requiring an `ActivityContext` to prevent `WindowManager$BadTokenException`. |
| `OrientationManager` | `activity` | Controls screen orientation using properties available exclusively on `Activity` (`requestedOrientation`). |

## Artifact Switching

By default, this sample depends on `:kmp-context-provider` (which uses AndroidX App Startup). To test with `:kmp-context-provider-no-deps` (zero transitive dependencies), update `samples/lib/build.gradle.kts`:

```kotlin
// Switch dependencies in androidMain:
// implementation(project(":kmp-context-provider"))
implementation(project(":kmp-context-provider-no-deps"))
```

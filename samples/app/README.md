# Sample Android Application (`:samples:app`)

This subproject is a standard Android application demonstrating `:kmp-context-provider` and `:samples:lib` in a real app runtime.

## Key Features

* **Zero Boilerplate:** Demonstrates that no manual `Application.onCreate()` initialization or context passing is required.
* **Interactive Testing:** `MainActivity` provides UI controls to test all sample features:
  * Persistent key-value storage (`KeyValueStorage`)
  * Low-memory listener registration (`MemoryWatcher`)
  * Displaying alert dialogs (`DialogPresenter`)
  * Toggling screen orientation (`OrientationManager`)

## Running

Build and assemble the APK with:

```bash
./gradlew :samples:app:assembleDebug -PincludeSamples=true
```

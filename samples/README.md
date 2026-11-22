# KMP Context Provider Samples

This directory contains sample projects demonstrating how to use `kmp-context-provider` in both Kotlin Multiplatform (KMP) libraries and Android applications.

## Modules Overview

* [`:samples:lib`](lib/README.md): A Kotlin Multiplatform library targeting Android and iOS. It uses `expect`/`actual` declarations to expose cross-platform utilities powered by `ContextProvider` on Android.
* [`:samples:app`](app/README.md): A native Android application demonstrating zero-boilerplate context usage with `:samples:lib` and `:kmp-context-provider`.

## Conditional Module Inclusion

By default, sample modules are automatically included when opening the project in **Android Studio** (`idea.active = true`).

When running from the **command line (CLI)**, sample modules are conditionally included to keep default builds fast. Pass `-PincludeSamples=true` to enable them:

```bash
./gradlew :samples:lib:assemble :samples:app:assemble -PincludeSamples=true
```

Or assemble all sample outputs:

```bash
./gradlew assemble -PincludeSamples=true
```

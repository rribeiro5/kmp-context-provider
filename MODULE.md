# Module kmp-context-provider

A lightweight library that automatically provides Android `Context`, `Application`, and `Activity` references across Kotlin Multiplatform (KMP) and Android projects without manual context passing.

```kotlin
import io.github.rribeiro5.context.ContextProvider

// Retrieve application context anywhere
val context = ContextProvider.applicationContext

// Retrieve active Activity (if currently resumed)
val activity = ContextProvider.activity
```

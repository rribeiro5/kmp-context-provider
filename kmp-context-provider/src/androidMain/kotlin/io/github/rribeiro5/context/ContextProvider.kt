package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

/**
 * Singleton provider that grants access to global Android [Context], [Application], and current [Activity] references.
 *
 * `ContextProvider` is automatically initialized at application launch (via AndroidX App Startup).
 * Once initialized, it tracks the active activity lifecycle without requiring manual context passing throughout your codebase.
 *
 * ### Key Features
 * - **Zero Boilerplate:** Automatically initialized on app startup; no manual setup in `Application.onCreate()` is required.
 * - **Global Context Access:** Easily retrieve the application context or [Application] instance from anywhere (e.g., repositories, singletons, network clients).
 * - **Current Activity Tracking:** Safely retrieve the currently resumed [Activity] (held via [WeakReference] to prevent memory leaks).
 * - **Java Interoperability:** All properties are annotated with `@JvmStatic` for seamless access from Java.
 *
 * ### Usage Example (Kotlin)
 * ```kotlin
 * // Access global application context
 * val context = ContextProvider.applicationContext
 * val packageName = context.packageName
 *
 * // Access global Application instance
 * val app = ContextProvider.application
 *
 * // Access current foreground Activity (if available)
 * val currentActivity = ContextProvider.activity
 * if (currentActivity != null) {
 *     // Perform Activity-dependent operations
 * }
 * ```
 *
 * ### Usage Example (Java)
 * ```java
 * Context context = ContextProvider.getApplicationContext();
 * Activity activity = ContextProvider.getActivity();
 * ```
 */
public object ContextProvider {
    private lateinit var _applicationContext: Context
    private var _activity: WeakReference<Activity>? = null

    /**
     * Returns the global application [Context].
     *
     * @throws IllegalStateException If `ContextProvider` has not been initialized yet.
     */
    @JvmStatic
    public val applicationContext: Context
        get() {
            check(::_applicationContext.isInitialized) { "Context has not been initialized." }
            return _applicationContext
        }

    /**
     * Returns the global [Application] instance.
     *
     * @throws IllegalStateException If `ContextProvider` has not been initialized yet.
     */
    @JvmStatic
    public val application: Application
        get() = applicationContext as Application

    /**
     * Returns the currently active (resumed) [Activity], or `null` if no activity is currently resumed.
     *
     * The activity reference is held as a [WeakReference] internally to avoid memory leaks.
     */
    @JvmStatic
    public val activity: Activity?
        get() = _activity?.get()

    /**
     * Returns the currently active (resumed) [Activity] as a [Context], or `null` if no activity is currently resumed.
     *
     * This is an alias for [activity].
     */
    @JvmStatic
    public val activityContext: Context?
        get() = activity

    internal fun initApplicationContext(context: Context) {
        _applicationContext = context
        application.registerActivityLifecycleCallbacks(CurrentActivityLifecycleCallbacks())
    }

    internal fun setActivity(activity: Activity) {
        _activity = WeakReference(activity)
    }

    internal fun clearActivity(activity: Activity) {
        if (_activity?.get() === activity) {
            _activity = null
        }
    }
}

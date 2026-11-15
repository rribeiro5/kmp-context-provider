package io.github.rribeiro5.context

import android.content.Context
import androidx.startup.Initializer

/**
 * Internal [Initializer] for AndroidX App Startup used to automatically initialize [ContextProvider] at application launch.
 *
 * **Warning:** This class is an internal API for `kmp-context-provider` and should not be used directly in application code.
 *
 * **Why it is public:** AndroidX App Startup requires [Initializer] implementations declared in `AndroidManifest.xml`
 * to be `public` so that the App Startup library can instantiate them reflectively during application startup.
 */
@InternalContextProviderApi
public class ApplicationContextInitializer : Initializer<Context> {
    /**
     * Initializes [ContextProvider] with the application context.
     *
     * @param context The application [Context] provided by AndroidX App Startup.
     * @return The initialized application [Context].
     */
    override fun create(context: Context): Context {
        ContextProvider.initApplicationContext(context.applicationContext)
        return context
    }

    /**
     * Returns an empty list of dependencies as [ApplicationContextInitializer] has no prerequisites.
     *
     * @return An empty list of initializer dependencies.
     */
    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}

package io.github.rribeiro5.context

import android.content.Context
import androidx.startup.Initializer

@InternalContextProviderApi
public class ApplicationContextInitializer : Initializer<Context> {
    override fun create(context: Context): Context {
        ContextProvider.initApplicationContext(context.applicationContext)
        return context
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}

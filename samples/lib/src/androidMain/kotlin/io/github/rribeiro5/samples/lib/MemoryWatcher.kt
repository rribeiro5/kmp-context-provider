package io.github.rribeiro5.samples.lib

import android.content.ComponentCallbacks
import android.content.res.Configuration
import io.github.rribeiro5.context.ContextProvider

public actual class MemoryWatcher {
    public actual fun registerMemoryTrimListener(onLowMemory: () -> Unit) {
        ContextProvider.application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                onLowMemory()
            }

            override fun onConfigurationChanged(newConfig: Configuration) {}
        })
    }
}

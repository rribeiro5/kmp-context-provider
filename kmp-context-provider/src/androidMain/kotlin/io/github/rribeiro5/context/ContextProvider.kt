package io.github.rribeiro5.context

import android.app.Application
import android.content.Context

public object ContextProvider {
    private lateinit var _applicationContext: Context

    @JvmStatic
    public val applicationContext: Context
        get() {
            check(::_applicationContext.isInitialized) { "Context has not been initialized." }
            return _applicationContext
        }

    @JvmStatic
    public val application: Application
        get() = applicationContext as Application

    internal fun initApplicationContext(context: Context) {
        _applicationContext = context
    }
}

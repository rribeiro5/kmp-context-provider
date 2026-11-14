package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

public object ContextProvider {
    private lateinit var _applicationContext: Context
    private var _activity: WeakReference<Activity>? = null

    @JvmStatic
    public val applicationContext: Context
        get() {
            check(::_applicationContext.isInitialized) { "Context has not been initialized." }
            return _applicationContext
        }

    @JvmStatic
    public val application: Application
        get() = applicationContext as Application

    @JvmStatic
    public val activity: Activity?
        get() = _activity?.get()

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

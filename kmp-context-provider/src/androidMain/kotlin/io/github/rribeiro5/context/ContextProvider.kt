package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

public object ContextProvider {
    private lateinit var _applicationContext: Context
    private var _activityReference: WeakReference<Activity>? = null
    private val activityLifecycleCallbacks = CurrentActivityLifecycleCallbacks()
    private var registeredApplication: Application? = null

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
        get() = _activityReference?.get()

    @JvmStatic
    public val activityContext: Context?
        get() = activity

    internal fun initApplicationContext(context: Context) {
        _applicationContext = context

        val application = context as Application
        if (registeredApplication !== application) {
            registeredApplication?.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
            registeredApplication = application
        }
    }

    internal fun setActivity(activity: Activity) {
        _activityReference = WeakReference(activity)
    }

    internal fun clearActivity(activity: Activity) {
        if (_activityReference?.get() === activity) {
            _activityReference = null
        }
    }
}

package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal class CurrentActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        ContextProvider.setActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        ContextProvider.clearActivity(activity)
    }

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        ContextProvider.clearActivity(activity)
    }
}

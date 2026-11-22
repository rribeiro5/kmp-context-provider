package io.github.rribeiro5.samples.lib

import android.content.pm.ActivityInfo
import io.github.rribeiro5.context.ContextProvider

public actual class OrientationManager {
    public actual fun setLandscape(enabled: Boolean): Boolean {
        // requestedOrientation is a property belonging strictly to Activity, not Context
        val activity = ContextProvider.activity ?: return false
        activity.runOnUiThread {
            activity.requestedOrientation = if (enabled) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
        return true
    }
}

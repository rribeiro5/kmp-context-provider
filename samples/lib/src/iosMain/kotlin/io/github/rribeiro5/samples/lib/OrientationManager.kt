package io.github.rribeiro5.samples.lib

import platform.Foundation.setValue
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

public actual class OrientationManager {
    public actual fun setLandscape(enabled: Boolean): Boolean {
        val targetOrientation = if (enabled) {
            UIDeviceOrientation.UIDeviceOrientationLandscapeLeft
        } else {
            UIDeviceOrientation.UIDeviceOrientationPortrait
        }
        UIDevice.currentDevice.setValue(targetOrientation, forKey = "orientation")
        return true
    }
}

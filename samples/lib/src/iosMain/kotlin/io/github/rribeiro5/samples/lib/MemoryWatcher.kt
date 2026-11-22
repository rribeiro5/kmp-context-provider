package io.github.rribeiro5.samples.lib

import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplicationDidReceiveMemoryWarningNotification

public actual class MemoryWatcher {
    public actual fun registerMemoryTrimListener(onLowMemory: () -> Unit) {
        NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIApplicationDidReceiveMemoryWarningNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            onLowMemory()
        }
    }
}

package io.github.rribeiro5.samples.lib

import platform.UIKit.UIApplication
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert

public actual class DialogPresenter {
    public actual fun showAlertDialog(title: String, message: String): Boolean {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return false
        val alert = UIAlertController.alertControllerWithTitle(
            title = title,
            message = message,
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(
            UIAlertAction.actionWithTitle(
                title = "OK",
                style = UIAlertActionStyleDefault,
                handler = null
            )
        )
        rootViewController.presentViewController(alert, animated = true, completion = null)
        return true
    }
}

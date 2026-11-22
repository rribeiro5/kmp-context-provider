package io.github.rribeiro5.samples.lib

import android.app.AlertDialog
import io.github.rribeiro5.context.ContextProvider

public actual class DialogPresenter {
    public actual fun showAlertDialog(title: String, message: String): Boolean {
        // AlertDialog requires an Activity Context to avoid WindowManager$BadTokenException
        val context = ContextProvider.activityContext ?: return false
        val activity = ContextProvider.activity ?: return false

        activity.runOnUiThread {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }
        return true
    }
}

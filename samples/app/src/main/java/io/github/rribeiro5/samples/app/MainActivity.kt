package io.github.rribeiro5.samples.app

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.rribeiro5.samples.lib.DialogPresenter
import io.github.rribeiro5.samples.lib.KeyValueStorage
import io.github.rribeiro5.samples.lib.MemoryWatcher
import io.github.rribeiro5.samples.lib.OrientationManager

class MainActivity : AppCompatActivity() {

    private val storage = KeyValueStorage()
    private val memoryWatcher = MemoryWatcher()
    private val dialogPresenter = DialogPresenter()
    private val orientationManager = OrientationManager()

    private var isLandscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleView = TextView(this).apply {
            text = "KMP Context Provider Sample"
            textSize = 20f
            setPadding(0, 250, 0, 32)
        }
        rootLayout.addView(titleView)

        // 1. KeyValueStorage (applicationContext)
        val storageStatus = TextView(this).apply {
            val storedVal = storage.getString("sample_key", "Default Value")
            text = "Storage value: $storedVal"
        }
        val storageButton = Button(this).apply {
            text = "Save Random Preference"
            setOnClickListener {
                val newPref = "Value #${(1..100).random()}"
                storage.putString("sample_key", newPref)
                storageStatus.text = "Storage value: $newPref"
            }
        }
        rootLayout.addView(storageStatus)
        rootLayout.addView(storageButton)

        // 2. MemoryWatcher (application)
        val memoryButton = Button(this).apply {
            text = "Register Memory Watcher"
            setOnClickListener {
                memoryWatcher.registerMemoryTrimListener {
                    Toast.makeText(this@MainActivity, "Low memory trim callback triggered", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this@MainActivity, "Memory trim listener registered", Toast.LENGTH_SHORT).show()
            }
        }
        rootLayout.addView(memoryButton)

        // 3. DialogPresenter (activityContext)
        val dialogButton = Button(this).apply {
            text = "Show Alert Dialog"
            setOnClickListener {
                val shown = dialogPresenter.showAlertDialog("Sample Dialog", "Triggered via ContextProvider.activityContext")
                if (!shown) {
                    Toast.makeText(this@MainActivity, "Failed to show dialog: no activityContext", Toast.LENGTH_SHORT).show()
                }
            }
        }
        rootLayout.addView(dialogButton)

        // 4. OrientationManager (activity)
        val orientationButton = Button(this).apply {
            text = "Toggle Orientation"
            setOnClickListener {
                isLandscape = !isLandscape
                orientationManager.setLandscape(isLandscape)
            }
        }
        rootLayout.addView(orientationButton)

        setContentView(rootLayout)
    }
}

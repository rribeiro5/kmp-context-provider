package io.github.rribeiro5.samples.lib

import android.content.Context
import io.github.rribeiro5.context.ContextProvider

public actual class KeyValueStorage {
    private val prefs by lazy {
        ContextProvider.applicationContext.getSharedPreferences("sample_prefs", Context.MODE_PRIVATE)
    }

    public actual fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    public actual fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

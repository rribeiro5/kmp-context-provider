package io.github.rribeiro5.samples.lib

import platform.Foundation.NSUserDefaults

public actual class KeyValueStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    public actual fun getString(key: String, defaultValue: String): String {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    public actual fun putString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }
}

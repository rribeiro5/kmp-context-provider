package io.github.rribeiro5.samples.lib

public expect class KeyValueStorage() {
    public fun getString(key: String, defaultValue: String): String
    public fun putString(key: String, value: String)
}

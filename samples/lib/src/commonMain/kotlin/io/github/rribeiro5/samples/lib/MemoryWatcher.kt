package io.github.rribeiro5.samples.lib

public expect class MemoryWatcher() {
    public fun registerMemoryTrimListener(onLowMemory: () -> Unit)
}

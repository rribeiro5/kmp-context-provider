package io.github.rribeiro5.context

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal API for kmp-context-provider and should not be used directly.",
)
@Retention(AnnotationRetention.BINARY)
public annotation class InternalContextProviderApi

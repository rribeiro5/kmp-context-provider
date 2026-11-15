package io.github.rribeiro5.context

/**
 * Annotation used to mark APIs that are public due to Android framework requirements,
 * but are internal to the `kmp-context-provider-no-deps` library.
 *
 * **Warning:** Declarations annotated with `@InternalContextProviderApi` are internal implementation details
 * and should not be used directly in application code. They may change or be removed without notice in future releases.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal API for kmp-context-provider-no-deps and should not be used directly.",
)
@Retention(AnnotationRetention.BINARY)
public annotation class InternalContextProviderApi

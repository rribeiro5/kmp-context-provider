package io.github.rribeiro5.context

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Internal [ContentProvider] used to automatically initialize [ContextProvider] at application launch.
 *
 * **Warning:** This class is an internal API for `kmp-context-provider-no-deps` and should not be used directly in application code.
 *
 * **Why it is public:** Android requires any [ContentProvider] declared in `AndroidManifest.xml` to be `public`
 * with a public zero-argument constructor so that the Android OS can instantiate it automatically on app startup.
 */
@InternalContextProviderApi
public class ApplicationContentProvider : ContentProvider() {
    /**
     * Called by the Android OS when the content provider is starting up.
     * Initializes [ContextProvider] with the application context.
     *
     * @return `true` if the provider was successfully loaded.
     */
    override fun onCreate(): Boolean {
        context?.applicationContext?.let(ContextProvider::initApplicationContext)
        return true
    }

    /**
     * Unused [ContentProvider] operation.
     *
     * @return Always returns `null`.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? = null

    /**
     * Unused [ContentProvider] operation.
     *
     * @return Always returns `null`.
     */
    override fun getType(uri: Uri): String? = null

    /**
     * Unused [ContentProvider] operation.
     *
     * @return Always returns `null`.
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    /**
     * Unused [ContentProvider] operation.
     *
     * @return Always returns `0`.
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    /**
     * Unused [ContentProvider] operation.
     *
     * @return Always returns `0`.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = 0
}

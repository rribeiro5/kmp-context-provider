package io.github.rribeiro5.context

import android.content.ContentValues
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ContentProviderController
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class ApplicationContextProviderTest {

    @BeforeTest
    @AfterTest
    fun resetState() {
        resetContextProvider()
    }

    @Test
    fun testOnCreateInitializesContextProvider() {
        val application = ApplicationProvider.getApplicationContext<android.app.Application>()
        val provider = ContentProviderController.of(ApplicationContextProvider()).create().get()

        assertSame(application, ContextProvider.applicationContext)
        assertSame(application, ContextProvider.application)
        assertTrue(provider.onCreate())
    }

    @Test
    fun testCrudMethodsAreNoOps() {
        val provider = ApplicationContextProvider()
        val uri = Uri.parse("content://test/path")
        val values = ContentValues()

        assertNull(provider.query(uri, null, null, null, null))
        assertNull(provider.getType(uri))
        assertNull(provider.insert(uri, values))
        assertEquals(0, provider.delete(uri, null, null))
        assertEquals(0, provider.update(uri, values, null, null))
    }

    private fun resetContextProvider() {
        val field = ContextProvider::class.java.getDeclaredField("_applicationContext")
        field.isAccessible = true
        field[ContextProvider] = null
    }
}

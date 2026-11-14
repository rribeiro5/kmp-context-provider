package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame

@RunWith(RobolectricTestRunner::class)
class ContextProviderTest {

    @BeforeTest
    @AfterTest
    fun resetState() {
        resetContextProvider()
    }

    @Test
    fun testUninitializedApplicationContextThrowsException() {
        val exception = assertFailsWith<IllegalStateException> {
            ContextProvider.applicationContext
        }
        assertEquals("Context has not been initialized.", exception.message)
    }

    @Test
    fun testUninitializedApplicationThrowsException() {
        val exception = assertFailsWith<IllegalStateException> {
            ContextProvider.application
        }
        assertEquals("Context has not been initialized.", exception.message)
    }

    @Test
    fun testInitApplicationContextStoresContextAndApplication() {
        val realApplication = ApplicationProvider.getApplicationContext<Application>()

        ContextProvider.initApplicationContext(realApplication)

        assertSame(realApplication, ContextProvider.applicationContext)
        assertSame(realApplication, ContextProvider.application)
    }

    @Test
    fun testActivityAndActivityContextAreNullWithoutResumedActivity() {
        assertNull(ContextProvider.activity)
        assertNull(ContextProvider.activityContext)
    }

    @Test
    fun testLifecycleCallbacksTrackOnlyTheCurrentResumedActivity() {
        val lifecycleCallbacks = CurrentActivityLifecycleCallbacks()
        val firstActivity = mockk<Activity>()
        val secondActivity = mockk<Activity>()

        lifecycleCallbacks.onActivityResumed(firstActivity)

        assertSame(firstActivity, ContextProvider.activity)
        assertSame(firstActivity, ContextProvider.activityContext)

        lifecycleCallbacks.onActivityPaused(secondActivity)

        assertSame(firstActivity, ContextProvider.activity)

        lifecycleCallbacks.onActivityPaused(firstActivity)

        assertNull(ContextProvider.activity)
        assertNull(ContextProvider.activityContext)
    }

    private fun resetContextProvider() {
        ContextProvider::class.java.getDeclaredField("_applicationContext").apply {
            isAccessible = true
            this[ContextProvider] = null
        }
        ContextProvider::class.java.getDeclaredField("_activity").apply {
            isAccessible = true
            this[ContextProvider] = null
        }
    }
}

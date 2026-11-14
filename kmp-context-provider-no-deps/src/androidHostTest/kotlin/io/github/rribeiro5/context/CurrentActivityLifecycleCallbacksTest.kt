package io.github.rribeiro5.context

import android.app.Activity
import android.os.Bundle
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.Runs
import io.mockk.verify
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class CurrentActivityLifecycleCallbacksTest {

    @Test
    fun testOnActivityResumedSetsActivity() {
        mockkObject(ContextProvider) {
            val activity = mockk<Activity>()
            every { ContextProvider.setActivity(any()) } just Runs

            CurrentActivityLifecycleCallbacks().onActivityResumed(activity)

            verify(exactly = 1) { ContextProvider.setActivity(activity) }
        }
    }

    @Test
    fun testOnActivityPausedClearsActivity() {
        mockkObject(ContextProvider) {
            val activity = mockk<Activity>()
            every { ContextProvider.clearActivity(any()) } just Runs

            CurrentActivityLifecycleCallbacks().onActivityPaused(activity)

            verify(exactly = 1) { ContextProvider.clearActivity(activity) }
        }
    }

    @Test
    fun testOtherLifecycleCallbacksDoNotCallContextProvider() {
        mockkObject(ContextProvider) {
            val activity = mockk<Activity>()
            val lifecycleCallbacks = CurrentActivityLifecycleCallbacks()

            lifecycleCallbacks.onActivityCreated(activity, null)
            lifecycleCallbacks.onActivityStarted(activity)
            lifecycleCallbacks.onActivityStopped(activity)
            lifecycleCallbacks.onActivitySaveInstanceState(activity, Bundle())
            lifecycleCallbacks.onActivityDestroyed(activity)

            verify(exactly = 0) { ContextProvider.setActivity(any()) }
            verify(exactly = 0) { ContextProvider.clearActivity(any()) }
        }
    }
}

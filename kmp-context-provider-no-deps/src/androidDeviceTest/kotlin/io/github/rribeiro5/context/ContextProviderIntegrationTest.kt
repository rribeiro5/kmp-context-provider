package io.github.rribeiro5.context

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertSame

class ContextProviderIntegrationTest {

    @Test
    fun testApplicationAndApplicationContextAreAvailableAfterInitialization() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        assertOnMainThread {
            assertSame(application, ContextProvider.application)
            assertSame(application, ContextProvider.applicationContext)
        }
    }

    @Test
    fun testActivityAndActivityContextAreAvailableAfterOpeningActivity() {
        val activity = launchActivity<FirstTestActivity>()
        assertCurrentActivity(activity)
        finishActivity(activity)
    }

    @Test
    fun testActivityAndActivityContextAreNullAfterPausingActivity() {
        val activity = launchActivity<FirstTestActivity>()
        assertCurrentActivity(activity)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.runOnMainSync {
            instrumentation.callActivityOnPause(activity)
        }
        assertNoCurrentActivity()

        finishActivity(activity)
    }

    @Test
    fun testActivityAndActivityContextAreAvailableAfterPausingAndResumingActivity() {
        val activity = launchActivity<FirstTestActivity>()
        assertCurrentActivity(activity)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.runOnMainSync {
            instrumentation.callActivityOnPause(activity)
        }
        assertNoCurrentActivity()

        instrumentation.runOnMainSync {
            instrumentation.callActivityOnResume(activity)
        }
        assertCurrentActivity(activity)

        finishActivity(activity)
    }

    @Test
    fun testActivityAndActivityContextAreNullAfterDestroyingActivity() {
        val activity = launchActivity<FirstTestActivity>()
        assertCurrentActivity(activity)

        finishActivity(activity)
        assertNoCurrentActivity()
    }

    @Test
    fun testLatestActivityIsAvailableAfterOpeningActivityOnTopOfAnother() {
        val firstActivity = launchActivity<FirstTestActivity>()
        assertCurrentActivity(firstActivity)

        val secondActivity = launchActivity<SecondTestActivity>()
        assertCurrentActivity(secondActivity)

        finishActivity(secondActivity)
        finishActivity(firstActivity)
    }

    private inline fun <reified T : Activity> launchActivity(): T {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return InstrumentationRegistry.getInstrumentation().startActivitySync(intent) as T
    }

    private fun finishActivity(activity: Activity) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            activity.finish()
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        // Allow time for system_server to process the activity finish request asynchronously
        // and dispatch the onActivityPaused lifecycle callback back to the main thread.
        Thread.sleep(300)
    }

    private fun assertCurrentActivity(activity: Activity) {
        assertOnMainThread {
            assertSame(activity, ContextProvider.activity)
            assertSame(activity, ContextProvider.activityContext)
        }
    }

    private fun assertNoCurrentActivity() {
        assertOnMainThread {
            assertNull(ContextProvider.activity)
            assertNull(ContextProvider.activityContext)
        }
    }

    private fun assertOnMainThread(assertions: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(assertions)
    }
}

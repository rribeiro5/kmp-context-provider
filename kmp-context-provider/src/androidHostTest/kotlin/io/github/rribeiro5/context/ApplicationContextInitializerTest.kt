package io.github.rribeiro5.context

import android.app.Application
import android.content.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.Runs
import io.mockk.verify
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(InternalContextProviderApi::class)
class ApplicationContextInitializerTest {

    @BeforeTest
    @AfterTest
    fun resetState() {
        ContextProvider::class.java.getDeclaredField("_applicationContext").apply {
            isAccessible = true
            this[ContextProvider] = null
        }
        ContextProvider::class.java.getDeclaredField("_activityReference").apply {
            isAccessible = true
            this[ContextProvider] = null
        }
        ContextProvider::class.java.getDeclaredField("registeredApplication").apply {
            isAccessible = true
            this[ContextProvider] = null
        }
    }

    @Test
    fun testCreateInitializesContextProviderAndReturnsContext() {
        mockkObject(ContextProvider) {
            val mockContext = mockk<Context>()
            val mockApplication = mockk<Application>()
            every { mockContext.applicationContext } returns mockApplication
            every { mockApplication.registerActivityLifecycleCallbacks(any()) } just Runs

            val initializer = ApplicationContextInitializer()
            val resultContext = initializer.create(mockContext)

            assertSame(mockContext, resultContext)

            verify(exactly = 1) { mockContext.applicationContext }
            verify(exactly = 1) { ContextProvider.initApplicationContext(mockApplication) }
        }
    }

    @Test
    fun testDependenciesReturnsEmptyList() {
        val initializer = ApplicationContextInitializer()
        val dependencies = initializer.dependencies()

        assertTrue(dependencies.isEmpty())
        assertEquals(emptyList(), dependencies)
    }
}

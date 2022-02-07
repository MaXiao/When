package com.xiaoism.time

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xiaoism.time.ui.main.TabHolderActivity
import com.xiaoism.time.ui.main.group.GroupListFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.xiaoism.time", appContext.packageName)
    }

    @get:Rule
    val testComposeRule = createAndroidComposeRule(TabHolderActivity::class.java)

    @Test
    fun testGroupAddBtn() {
        val button = testComposeRule.onNode(hasTestTag("addButton"), true)

        button.assertIsDisplayed()
        button.performClick()
    }
}
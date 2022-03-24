package com.xiaoism.time

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.accompanist.pager.ExperimentalPagerApi
import com.xiaoism.time.ui.MainActivity

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
@OptIn(ExperimentalPagerApi::class)
class ExampleInstrumentedTest {
    @get:Rule
    val testComposeRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testHome() {
        // Check home page is visible on launch
        val home = testComposeRule.onNode(hasTestTag("Home"), true)
        home.assertIsDisplayed()
    }

    @Test
    fun testTabs() {
        // Check two tabs are visible
        val groupTab = testComposeRule.onNode(hasTestTag("Groups"))
        groupTab.assertIsDisplayed()

        val personsTab = testComposeRule.onNode(hasTestTag("Persons"))
        personsTab.assertIsDisplayed()

        // Check Group view is visible on launch
        val groupView = testComposeRule.onNode(hasTestTag("GroupListView"))
        groupView.assertIsDisplayed()

        // switch to person view when click person tab
        personsTab.performClick()
        val personView = testComposeRule.onNode(hasTestTag("PersonListView"))
        personView.assertIsDisplayed()

        // switch to group view when click group tab
        groupTab.performClick()
        groupView.assertIsDisplayed()
    }
}
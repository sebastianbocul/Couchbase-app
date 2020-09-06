package com.sebix.couchbase_app.ui

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.pinder.app.util.RepeatRule
import com.pinder.app.util.RepeatTest
import com.sebix.couchbase_app.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentManagerTest {
    @Rule
    @JvmField
    var repeatRule: RepeatRule = RepeatRule()

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    @RepeatTest(1)
    fun test_start_componentsDisplayed() {
        Thread.sleep(100)

        onView(withId(R.id.main_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.number1)).check(matches(isDisplayed()))
        onView(withId(R.id.number2)).check(matches(isDisplayed()))
        onView(withId(R.id.calculate_button)).check(matches(isDisplayed()))
        onView(withId(R.id.save_button)).check(matches(isDisplayed()))
        onView(withId(R.id.logo_small)).check(matches(isDisplayed()))
    }

    @Test
    @RepeatTest(10)
    fun test_addNumbersToDb_restartActivity_checkIfNewDataLoaded() {
        val n1 = Random(100000)
        val n2 = Random(100000)
        Thread.sleep(100)
        onView(withId(R.id.number1)).perform(click())
        onView(withId(R.id.number1)).perform(clearText())
        onView(withId(R.id.number1)).perform(typeText(n1.toString()))

        onView(withId(R.id.number2)).perform(click())
        onView(withId(R.id.number2)).perform(clearText())
        onView(withId(R.id.number2)).perform(typeText(n2.toString()))

        onView(withText(R.string.save_button)).perform(click())
        Thread.sleep(1000)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(5000)
        Log.d("TEST LOG", "test_addNumbersToDb_restartActivity_checkIfNewDataLoaded: ${n1.toString()}" )
//        onView(withId(R.id.number1)).perform(equals())
//        onView(allOf(withId(R.id.main_fragment))).check(matches(isEditTextValueEqualTo(R.id.edtPass, mStringToBetyped)));
        onView(withId(R.id.number2)).perform(click())
        onView(withId(R.id.number2)).perform(clearText())
        onView(withId(R.id.number2)).perform(typeText(n1.toString()))
        Thread.sleep(5000)

        onView(withId(R.id.number1)).check(matches(withText(n1.toString())))
//        onView(allOf(withText(endsWith(n1.toString())))).check(matches(isDisplayed()));
//        onView(withText(n2.toString())).check(matches(isDisplayed()));
    }
}
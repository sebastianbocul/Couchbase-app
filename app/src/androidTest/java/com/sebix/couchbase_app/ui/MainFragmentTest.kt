package com.sebix.couchbase_app.ui

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sebix.couchbase_app.R
import com.sebix.couchbase_app.utils.EspressoIdlingResource
import com.sebix.couchbase_app.utils.RepeatRule
import com.sebix.couchbase_app.utils.RepeatTest
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random


@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentManagerTest {
    @Rule
    @JvmField
    var repeatRule: RepeatRule = RepeatRule()

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    @RepeatTest(3)
    fun test_start_componentsDisplayed() {
        Thread.sleep(100)
        onView(withId(R.id.main_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.number1)).check(matches(isDisplayed()))
        onView(withId(R.id.number2)).check(matches(isDisplayed()))
        onView(withId(R.id.calculate_button)).check(matches(isDisplayed()))
        onView(withId(R.id.save_button)).check(matches(isDisplayed()))
        onView(withId(R.id.logo_small)).check(matches(isDisplayed()))
        onView(withId(R.id.mode_button)).check(matches(isDisplayed()))
    }

    @Test
    @RepeatTest(5)
    fun test_addNumbers_clickSave_restartActivity_checkIfDataLoaded() {
        val n1 = Random.nextInt(0, 1000000)
        val n2 = Random.nextInt(0, 1000000)
        Thread.sleep(1000)
        onView(withId(R.id.number1)).perform(click())
        onView(withId(R.id.number1)).perform(clearText())
        onView(withId(R.id.number1)).perform(typeText(n1.toString()))

        onView(withId(R.id.number2)).perform(click())
        onView(withId(R.id.number2)).perform(clearText())
        onView(withId(R.id.number2)).perform(typeText(n2.toString()))

        Thread.sleep(500)
        onView(withId(R.id.save_button)).perform(click())

        Thread.sleep(500)
        restartActivity()
        Thread.sleep(500)

        onView(withText(n1.toString())).check(matches(isDisplayed()));
        onView(withText(n2.toString())).check(matches(isDisplayed()));
    }

    @Test
    @RepeatTest(5)
    fun test_addNumbers_clickCalculate_checkProgressBarDisplayed_clickCancel_checkIfProgressBarNotDisplayed() {
        val n1 = 0
        val n2 = 999999
        Thread.sleep(1000)
        onView(withId(R.id.number1)).perform(click())
        onView(withId(R.id.number1)).perform(clearText())
        onView(withId(R.id.number1)).perform(typeText(n1.toString()))

        onView(withId(R.id.number2)).perform(click())
        onView(withId(R.id.number2)).perform(clearText())
        onView(withId(R.id.number2)).perform(typeText(n2.toString()))

        onView(withText(R.string.calculate_button)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel_button)).check(doesNotExist())

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))

        Thread.sleep(500)
        onView(withText(R.string.calculate_button)).perform(click())
        Thread.sleep(500)
        onView(withText(R.string.calculate_button)).check(doesNotExist())
        onView(withText(R.string.cancel_button)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))

        Thread.sleep(500)
        onView(withText(R.string.cancel_button)).perform(click())
        Thread.sleep(500)

        onView(withText(R.string.calculate_button)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel_button)).check(doesNotExist())
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }

    @Test
    @RepeatTest(10)
    fun test_addNumbers_clickCalculate_waitTillComplete_checkIfProgressBarNotDisplayed() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        val n1 = Random.nextInt(0, 100000)
        val n2 = Random.nextInt(0, 100000)
        Thread.sleep(1000)
        onView(withId(R.id.number1)).perform(click())
        onView(withId(R.id.number1)).perform(clearText())
        onView(withId(R.id.number1)).perform(typeText(n1.toString()))

        onView(withId(R.id.number2)).perform(click())
        onView(withId(R.id.number2)).perform(clearText())
        onView(withId(R.id.number2)).perform(typeText(n2.toString()))

        onView(withText(R.string.calculate_button)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel_button)).check(doesNotExist())

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))

        onView(withText(R.string.calculate_button)).perform(click())
        //idlingresouce await

        onView(withText(R.string.calculate_button)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel_button)).check(doesNotExist())
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    @RepeatTest(5)
    fun test_nightMode() {
        Thread.sleep(1000)
        onView(withId(R.id.mode_button)).perform(click())

        onView(withId(R.id.main_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.number1)).check(matches(isDisplayed()))
        onView(withId(R.id.number2)).check(matches(isDisplayed()))
        onView(withId(R.id.calculate_button)).check(matches(isDisplayed()))
        onView(withId(R.id.save_button)).check(matches(isDisplayed()))
        onView(withId(R.id.logo_small)).check(matches(isDisplayed()))
        onView(withId(R.id.mode_button)).check(matches(isDisplayed()))
    }


    fun restartActivity() {
        var scenario = activityScenarioRule.getScenario()
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.recreate()
    }
}
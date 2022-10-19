package com.example.helloworld

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RavenButtonTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun clickerIncrement() {
        // Set Raven Dollars to 0
        onView(withId(R.id.ravenDollars)).perform(replaceText("0"))

        // Confirm that Raven Dollars start at zero
        onView(withId(R.id.ravenDollars)).check(matches(withText("0")))

        // Click the button
        onView(withId(R.id.ravenButton)).perform(click())

        // Confirm that value increments
        onView(withId(R.id.ravenDollars)).check(matches(withText("1")))

        // Click again and confirm new value
        onView(withId(R.id.ravenButton)).perform(click())
        onView(withId(R.id.ravenDollars)).check(matches(withText("2")))
    }
}
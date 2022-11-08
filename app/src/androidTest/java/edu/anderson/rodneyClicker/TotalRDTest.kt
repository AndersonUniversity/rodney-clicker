package edu.anderson.rodneyClicker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TotalRDTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun viewTotalRD() {
        for (i in 1..10) {
            onView(withId(R.id.ravenButton)).perform(click())
        }

        // Confirm that Raven Dollars are 10
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$10")))
        onView(withId(R.id.totalRavenDollars)).check(matches(withText("10")))

        // Click the store page button
        onView(withId(R.id.store_button)).perform(click())

        // Click the upgrade button
        onView(withId(R.id.buy_upgradeButton)).perform(click())

        // Click the home page button
        onView(withId(R.id.home_button)).perform(click())

        // Wait 1 second for ui to update
        Thread.sleep(1010)

        // Confirm that value increments
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$1")))
        onView(withId(R.id.totalRavenDollars)).check(matches(withText("11")))
    }
}
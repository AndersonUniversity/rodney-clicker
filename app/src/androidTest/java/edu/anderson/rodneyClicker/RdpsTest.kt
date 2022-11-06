package edu.anderson.rodneyClicker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test

class RdpsTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun clickerIncrement() {
        // Confirm that Raven Dollars start at zero
        onView(withId(R.id.ravenDollarsPerSecond)).check(matches(withText("Raven Dollars Per Second: 0")))

        // Click the Rodney button 10 times
        for (i in 1..10) {
            onView(withId(R.id.ravenButton)).perform(click())
        }

        // Open the store page
        onView(withId(R.id.store_button)).perform(click())

        // Buy a Rodney
        onView(withId(R.id.buy_upgradeButton)).perform(click())

        // Return to the main page
        onView(withId(R.id.home_button)).perform(click())

        // Wait 10 seconds
        Thread.sleep(10000)

        // Confirm that Raven Dollars per second is 1
        onView(withId(R.id.ravenDollarsPerSecond)).check(matches(withText("Raven Dollars Per Second: 1")))
    }
}

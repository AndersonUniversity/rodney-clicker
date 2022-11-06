package edu.anderson.rodneyClicker

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
class BasicUpgradeTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun buyUpgrade() {
        // Set Raven Dollars to 100
        onView(withId(R.id.ravenDollars)).perform(replaceText("R$100"))

        // Confirm that Raven Dollars start at 100
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$100")))

        // Click the store page button
        onView(withId(R.id.store_button)).perform(click())

        // Click the upgrade button
        onView(withId(R.id.buy_upgradeButton)).perform(click())

        // Click the home page button
        onView(withId(R.id.home_button)).perform(click())

        // Confirm that value increments
        onView(withId(R.id.total_rodneys)).check(matches(withText("Total Rodneys: 1")))
    }
}

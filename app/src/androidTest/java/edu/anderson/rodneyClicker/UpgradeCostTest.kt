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

@RunWith(AndroidJUnit4::class)
class UpgradeCostTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun checkCost() {
        //click 25 times
        for (i in 1..122) {
            onView(withId(R.id.ravenButton)).perform(click())
        }

        // Confirm that Raven Dollars are 200
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$122")))

        // Click the store page button
        onView(withId(R.id.store_button)).perform(click())

        //Check that the cost is equal to 10
        onView(withId(R.id.rodney_cost_text)).check(matches(withText("R$10")))

        // Click the buy button
        onView(withId(R.id.buy_rodneyButton)).perform(click())

        // Check the cost has gone up
        var cost = (10 * 1.15).toInt()
        onView(withId(R.id.rodney_cost_text)).check(matches(withText("R$$cost")))

        // Buy again
        onView(withId(R.id.buy_rodneyButton)).perform(click())

        // Check the cost has gone up
        cost = (10 * 1.15 * 2).toInt()
        onView(withId(R.id.rodney_cost_text)).check(matches(withText("R$$cost")))

        // buy helios
        onView(withId(R.id.buy_heliosButton)).perform(click())

        // check the cost has gone up
        cost = (100 * 1.15).toInt()
        onView(withId(R.id.helios_cost_text)).check(matches(withText("R$$cost")))
    }
}
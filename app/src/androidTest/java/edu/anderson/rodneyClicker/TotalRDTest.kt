package edu.anderson.rodneyClicker

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test

class TotalRDTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View>? {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }

    @Test
    fun viewTotalRD() {
        for (i in 1..10) {
            onView(withId(R.id.ravenButton)).perform(click())
        }

        // Confirm that Raven Dollars are 10
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$10")))
        onView(withId(R.id.totalRavenDollars)).check(matches(withText("Total Raven Dollars: 10")))

        // Click the store page button
        onView(withId(R.id.store_button)).perform(click())

        // Click the upgrade button
        onView(withId(R.id.buy_rodneyButton)).perform(click())

        // Click the home page button
        onView(withId(R.id.home_button)).perform(click())

        // Wait 1 second for ui to update
        Thread.sleep(1000)

        val currRD: ViewInteraction = onView(withId(R.id.ravenDollars))
        var text = getText(currRD)
        text = text.substring(2, text.length)
        val num = text.toInt()
        val allRD = onView(withId(R.id.totalRavenDollars))
        var text2 = getText(allRD)
        text2 = text2.substring(21, text2.length)
        val num2 = text2.toInt()

        // Verify that total Raven Dollars is higher than the current Raven Dollars
        assert(num2 > num)
    }
}

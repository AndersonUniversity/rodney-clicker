package edu.anderson.rodneyClicker

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SaveDataTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun saveData() {
        // Click the Rodney button 25 times
        for (i in 1..25) {
            onView(withId(R.id.ravenButton)).perform(ViewActions.click())
        }

        // Confirm that value is 25
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$25")))

        // Open the store page
        onView(withId(R.id.store_button)).perform(ViewActions.click())

        // Buy 2 Rodney
        onView(withId(R.id.buy_rodneyButton)).perform(ViewActions.doubleClick())

        // Return to the main page
        onView(withId(R.id.home_button)).perform(ViewActions.click())

        // Recreate activity
        rule.scenario.close()
        launch(MainActivity::class.java)

        // Wait 1 second for ui to update
        Thread.sleep(1000)

        // Check to see if the values are saved on the ui
        onView(withId(R.id.total_rodneys)).check(matches(withText("2")))
        onView(withId(R.id.ravenDollarsPerSecond)).check(matches(withText("2 R$/s")))
    }
}

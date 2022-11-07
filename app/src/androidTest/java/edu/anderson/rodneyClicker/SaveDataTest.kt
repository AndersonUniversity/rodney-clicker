package edu.anderson.rodneyClicker

import android.content.Context
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SaveDataTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun saveData() {
        // Click the Rodney button 115 times
        for (i in 1..115) {
            onView(withId(R.id.ravenButton)).perform(ViewActions.click())
        }

        // Confirm that value is 115
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$115")))

        // Open the store page
        onView(withId(R.id.store_button)).perform(ViewActions.click())

        // Buy a Rodney
        onView(withId(R.id.buy_rodneyButton)).perform(ViewActions.click())

        // Buy a multiplier
        onView(withId(R.id.buy_multiplier_rodney)).perform(ViewActions.click())

        // Return to the main page
        onView(withId(R.id.home_button)).perform(ViewActions.click())

        // Recreate activity
        rule.scenario.close()
        launch(MainActivity::class.java)

        // Check that the value is stored in shared preferences
        val targetContext = getInstrumentation().targetContext
        val sharedPreferences = targetContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedRavenDollars = sharedPreferences.getString("Raven_Dollars", "0")
        val savedRodneyClickers = sharedPreferences.getString("Rodney_Clickers", "0")
        val savedRodneyClickersMultipliers = sharedPreferences.getString("Rodney_Multipliers", "0")

        assert(savedRavenDollars == "5")
        assert(savedRodneyClickers == "1")
        assert(savedRodneyClickersMultipliers == "2")

        // Wait 1 second for ui to update
        Thread.sleep(1000)

        // Check to see if the values are saved on the ui
        onView(withId(R.id.ravenDollars)).check(matches(withText("R$7")))
        onView(withId(R.id.total_rodneys)).check(matches(withText("Total Rodneys: 1")))
        onView(withId(R.id.ravenDollarsPerSecond)).check(matches(withText("Raven Dollars Per Second: 2")))
    }
}

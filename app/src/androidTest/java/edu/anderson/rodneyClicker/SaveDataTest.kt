package edu.anderson.rodneyClicker

import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher

@RunWith(AndroidJUnit4::class)
class SaveDataTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View>? {
                return isAssignableFrom(TextView::class.java)
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

        // Wait 1 second for ui to update
        Thread.sleep(1000)

        // Check to see if the values are saved on the ui
        val currRD:ViewInteraction = onView(withId(R.id.ravenDollars))
        var text = getText(currRD)
        text = text.substring(2,text.length)
        var num = text.toInt()
        val allRD = onView(withId(R.id.totalRavenDollars))
        var num2 = getText(allRD).toInt()
        check(num == (num2-110))
        onView(withId(R.id.total_rodneys)).check(matches(withText("Total Rodneys: 1")))
        onView(withId(R.id.ravenDollarsPerSecond)).check(matches(withText("Raven Dollars Per Second: 2")))
    }
}

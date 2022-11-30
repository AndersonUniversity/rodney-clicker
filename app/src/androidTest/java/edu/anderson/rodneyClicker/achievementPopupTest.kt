package edu.anderson.rodneyClicker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.doubleClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class achievementPopupTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun getAchievement() {
        // Click the Rodney button 1 times
        onView(withId(R.id.ravenButton)).perform(click())
        // Confirm that Raven Dollars are 10
        Thread.sleep(1000)
        onView(withId(R.id.achievementPopup)).check(matches(withText("\nWhat's this?\nclick the raven")))
        // Click the store page button
        for (i in 1..50) {
            onView(withId(R.id.ravenButton)).perform(doubleClick())
        }
        Thread.sleep(1000)
        onView(withId(R.id.achievementPopup)).check(matches(withText("\nSore Thumb\nclick 100 times")))
    }
}

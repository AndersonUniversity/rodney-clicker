package com.example.helloworld

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun testIncreaseNum() {
        val view = MainActivity().findViewById<View>(R.id.ravenDollars)
        assertEquals(1, MainActivity().increaseNum(view))
    }
}

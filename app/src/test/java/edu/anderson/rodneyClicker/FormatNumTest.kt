package edu.anderson.rodneyClicker

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatNumTest {
    @Test
    fun formatNumberTest() {
        assertEquals("R$1", FormatNum.formatNumber(1))
        assertEquals("R$1.00K", FormatNum.formatNumber(1000))
        assertEquals("R$1.00M", FormatNum.formatNumber(1000000))
        assertEquals("R$1.00B", FormatNum.formatNumber(1000000000))
        assertEquals("R$1.00T", FormatNum.formatNumber(1000000000000))
        assertEquals("R$1.00Q", FormatNum.formatNumber(1000000000000000000))
    }
}

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

    fun formatNumberTRDTest() {
        assertEquals("Total Raven Dollars: 1", FormatNum.formatNumberTRD(1))
        assertEquals("Total Raven Dollars: 1.00K", FormatNum.formatNumberTRD(1000))
        assertEquals("Total Raven Dollars: 1.00M", FormatNum.formatNumberTRD(1000000))
        assertEquals("Total Raven Dollars: 1.00B", FormatNum.formatNumberTRD(1000000000))
        assertEquals("Total Raven Dollars: 1.00T", FormatNum.formatNumberTRD(1000000000000))
        assertEquals("Total Raven Dollars: 1.00Q", FormatNum.formatNumberTRD(1000000000000000000))
    }

    fun formatNumberRDPS() {
        assertEquals("+1/s", FormatNum.formatNumberRDPS(1))
        assertEquals("+1.00K/s", FormatNum.formatNumberRDPS(1000))
        assertEquals("+1.00M/s", FormatNum.formatNumberRDPS(1000000))
        assertEquals("+1.00B/s", FormatNum.formatNumberRDPS(1000000000))
        assertEquals("+1.00T/s", FormatNum.formatNumberRDPS(1000000000000))
        assertEquals("+1.00Q/s", FormatNum.formatNumberRDPS(1000000000000000000))
    }
}

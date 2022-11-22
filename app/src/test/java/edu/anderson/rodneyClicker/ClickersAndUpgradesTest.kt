package edu.anderson.rodneyClicker

import org.junit.Assert.assertEquals
import org.junit.Test

class ClickersAndUpgradesTest {
    @Test
    fun addClickerTest() {
        val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
        val helios = ClickersAndUpgrades.AutoClicker(10, 0, 1)
        ClickersAndUpgrades.addClicker(5, 2, rodney, helios)
        assertEquals(5, rodney.numOwned)
        assertEquals(2, helios.numOwned)
        assertEquals(25, (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier))
    }

    @Test
    fun addMultiplierTest() {
        val rodney = ClickersAndUpgrades.AutoClicker(1, 1, 1)
        val helios = ClickersAndUpgrades.AutoClicker(10, 1, 1)
        ClickersAndUpgrades.addMultiplier(2, 2, rodney, helios)
        assertEquals(2, rodney.multiplier)
        assertEquals(2, helios.multiplier)
        assertEquals(22, (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier))
    }
}

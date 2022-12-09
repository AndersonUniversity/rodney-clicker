package edu.anderson.rodneyClicker

import org.junit.Assert.assertEquals
import org.junit.Test

class ClickersAndUpgradesTest {
    @Test
    fun addClickerTest() {
        val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
        val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)
        val eternalFlame = ClickersAndUpgrades.AutoClicker(15, 0, 1)
        val koontz = ClickersAndUpgrades.AutoClicker(30, 0, 1)
        val joshTandy = ClickersAndUpgrades.AutoClicker(50, 0, 1)
        ClickersAndUpgrades.addClicker(5, 2, 2, 2, 2, rodney, helios, eternalFlame, koontz, joshTandy)
        assertEquals(5, rodney.numOwned)
        assertEquals(2, helios.numOwned)
        assertEquals(2, eternalFlame.numOwned)
        assertEquals(2, koontz.numOwned)
        assertEquals(2, joshTandy.numOwned)
        assertEquals(205, (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier) + (eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier) + (koontz.dps * koontz.numOwned * koontz.multiplier) + (joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier))
    }

    @Test
    fun addMultiplierTest() {
        val rodney = ClickersAndUpgrades.AutoClicker(1, 1, 1)
        val helios = ClickersAndUpgrades.AutoClicker(5, 1, 1)
        val eternalFlame = ClickersAndUpgrades.AutoClicker(15, 1, 1)
        val koontz = ClickersAndUpgrades.AutoClicker(30, 1, 1)
        val joshTandy = ClickersAndUpgrades.AutoClicker(50, 1, 1)
        ClickersAndUpgrades.addMultiplier(2, 2, 2, 2, 2, rodney, helios, eternalFlame, koontz, joshTandy)
        assertEquals(2, rodney.multiplier)
        assertEquals(2, helios.multiplier)
        assertEquals(2, eternalFlame.multiplier)
        assertEquals(2, koontz.multiplier)
        assertEquals(2, joshTandy.multiplier)
        assertEquals(202, (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier) + (eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier) + (koontz.dps * koontz.numOwned * koontz.multiplier) + (joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier))
    }
}

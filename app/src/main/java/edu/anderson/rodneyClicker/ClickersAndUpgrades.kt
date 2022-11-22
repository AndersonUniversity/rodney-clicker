package edu.anderson.rodneyClicker

object ClickersAndUpgrades {
    class AutoClicker(var dps: Int, var numOwned: Int, var multiplier: Int) {
        fun buy() {
            numOwned += 1
        }
        fun buyMultiplier() {
            multiplier += 1
        }
    }

    fun addClicker(numRodneyUpgrades: Int, numHeliosUpgrades: Int, numEternalFlameUpgrades: Int, numKoontzUpgrade: Int, numJoshTandyUpgrades: Int, rodney: AutoClicker, helios: AutoClicker, eternalFlame: AutoClicker, koontz: AutoClicker, joshTandy: AutoClicker) {
        val newRodneys = numRodneyUpgrades - rodney.numOwned
        val newHelios = numHeliosUpgrades - helios.numOwned
        val newEternalFlame = numEternalFlameUpgrades - eternalFlame.numOwned
        val newKoontz = numKoontzUpgrade - koontz.numOwned
        val newJoshTandy = numJoshTandyUpgrades - joshTandy.numOwned
        for (i in 1..newRodneys) {
            rodney.buy()
        }
        for (i in 1..newHelios) {
            helios.buy()
        }
        for (i in 1..newEternalFlame) {
            eternalFlame.buy()
        }
        for (i in 1..newKoontz) {
            koontz.buy()
        }
        for (i in 1..newJoshTandy) {
            joshTandy.buy()
        }
    }

    fun addMultiplier(numRodneyMultipliers: Int, numHeliosMultipliers: Int, numEternalFlameMultiplier: Int, numKoontzUpgrade: Int, numJoshTandyUpgrade: Int, rodney: AutoClicker, helios: AutoClicker, eternalFlame: AutoClicker, koontz: AutoClicker, joshTandy: AutoClicker) {
        val newRodneyMultipliers = numRodneyMultipliers - rodney.multiplier
        val newHeliosMultipliers = numHeliosMultipliers - helios.multiplier
        val newEternalFlameMultipliers = numEternalFlameMultiplier - eternalFlame.multiplier
        val newKoontzMultipliers = numKoontzUpgrade - koontz.multiplier
        val newJoshTandyMultipliers = numJoshTandyUpgrade - joshTandy.multiplier
        for (i in 1..newRodneyMultipliers) {
            rodney.buyMultiplier()
        }
        for (i in 1..newHeliosMultipliers) {
            helios.buyMultiplier()
        }
        for (i in 1..newEternalFlameMultipliers) {
            eternalFlame.buyMultiplier()
        }
        for (i in 1..newKoontzMultipliers) {
            koontz.buyMultiplier()
        }
        for (i in 1..newJoshTandyMultipliers) {
            joshTandy.buyMultiplier()
        }
    }
}

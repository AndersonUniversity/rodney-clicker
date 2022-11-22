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

    fun addClicker(numRodneyUpgrades: Int, numHeliosUpgrades: Int, rodney: AutoClicker, helios: AutoClicker) {
        val newRodneys = numRodneyUpgrades - rodney.numOwned
        val newHelios = numHeliosUpgrades - helios.numOwned
        for (i in 1..newRodneys) {
            rodney.buy()
        }
        for (i in 1..newHelios) {
            helios.buy()
        }
    }

    fun addMultiplier(numRodneyMultipliers: Int, numHeliosMultipliers: Int, rodney: AutoClicker, helios: AutoClicker) {
        val newRodneyMultipliers = numRodneyMultipliers - rodney.multiplier
        val newHeliosMultipliers = numHeliosMultipliers - helios.multiplier
        for (i in 1..newRodneyMultipliers) {
            rodney.buyMultiplier()
        }
        for (i in 1..newHeliosMultipliers) {
            helios.buyMultiplier()
        }
    }
}

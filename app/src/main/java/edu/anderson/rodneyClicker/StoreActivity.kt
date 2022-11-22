package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    private var numRavenDollars = 0
    private var numTotalRavenDollars = 0
    private var numTotalRodneyUpgrades = 0
    private var numTotalRodneyMultipliers = 0
    private var numTotalHeliosUpgrades = 0
    private var numTotalHeliosMultipliers = 0
    private var numTotalEternalFlameUpgrades = 0
    private var numTotalEternalFlameMultipliers = 0
    private var numTotalKoontzUpgrades = 0
    private var numTotalKoontzMultipliers = 0
    private var numTotalJoshTandyUpgrades = 0
    private var numTotalJoshTandyMultipliers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        numRavenDollars = sharedPref.getString("Raven_Dollars", "0")?.toInt() ?: 0
        numTotalRavenDollars = sharedPref.getString("Total_Raven_Dollars", "0")?.toInt() ?: 0
        numTotalRodneyUpgrades = sharedPref.getString("Rodney_Clickers", "0")?.toInt() ?: 0
        numTotalRodneyMultipliers = sharedPref.getString("Rodney_Multipliers", "0")?.toInt() ?: 0
        numTotalHeliosUpgrades = sharedPref.getString("Helios_Clickers", "0")?.toInt() ?: 0
        numTotalHeliosMultipliers = sharedPref.getString("Helios_Multipliers", "0")?.toInt() ?: 0
        numTotalEternalFlameUpgrades = sharedPref.getString("Eternal_Flame_Clickers", "0")?.toInt() ?: 0
        numTotalEternalFlameMultipliers = sharedPref.getString("Eternal_Flame_Multipliers", "0")?.toInt() ?: 0
        numTotalKoontzUpgrades = sharedPref.getString("Koontz_Clickers", "0")?.toInt() ?: 0
        numTotalKoontzMultipliers = sharedPref.getString("Koontz_Multipliers", "0")?.toInt() ?: 0
        numTotalJoshTandyUpgrades = sharedPref.getString("Josh_Tandy_Clickers", "0")?.toInt() ?: 0
        numTotalJoshTandyMultipliers = sharedPref.getString("Josh_Tandy_Multipliers", "0")?.toInt() ?: 0

        findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
        gameLoop(this)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        val i = Intent(this@StoreActivity, MainActivity::class.java)
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", numRavenDollars.toString())
            putString("Total_Raven_Dollars", numTotalRavenDollars.toString())
            putString("Rodney_Clickers", numTotalRodneyUpgrades.toString())
            putString("Rodney_Multipliers", numTotalRodneyMultipliers.toString())
            putString("Helios_Clickers", numTotalHeliosUpgrades.toString())
            putString("Helios_Multipliers", numTotalHeliosMultipliers.toString())
            putString("Eternal_Flame_Clickers", numTotalEternalFlameUpgrades.toString())
            putString("Eternal_Flame_Multipliers", numTotalEternalFlameMultipliers.toString())
            putString("Koontz_Clickers", numTotalKoontzUpgrades.toString())
            putString("Koontz_Multipliers", numTotalKoontzMultipliers.toString())
            putString("Josh_Tandy_Clickers", numTotalJoshTandyUpgrades.toString())
            putString("Josh_Tandy_Multipliers", numTotalJoshTandyMultipliers.toString())
        }.apply()
        startActivity(i)
    }

    val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
    val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)
    val eternalFlame = ClickersAndUpgrades.AutoClicker(10, 0, 1)
    val koontz = ClickersAndUpgrades.AutoClicker(15, 0, 1)
    val joshTandy = ClickersAndUpgrades.AutoClicker(20, 0, 1)

    private fun gameLoop(view: StoreActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    ClickersAndUpgrades.addClicker(numTotalRodneyUpgrades, numTotalHeliosUpgrades, numTotalEternalFlameUpgrades, numTotalKoontzUpgrades, numTotalJoshTandyUpgrades, rodney, helios, eternalFlame, koontz, joshTandy)
                    ClickersAndUpgrades.addMultiplier(numTotalRodneyMultipliers, numTotalHeliosMultipliers, numTotalEternalFlameMultipliers, numTotalKoontzMultipliers, numTotalJoshTandyMultipliers, rodney, helios, eternalFlame, koontz, joshTandy)
                    val rodneyAdd = (rodney.dps * rodney.numOwned * rodney.multiplier)
                    val heliosAdd = (helios.dps * helios.numOwned * helios.multiplier)
                    val eternalFlameAdd = (eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier)
                    val koontzAdd = (koontz.dps * koontz.numOwned * koontz.multiplier)
                    val joshTandyAdd = (joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier)
                    val toAdd = rodneyAdd + heliosAdd + eternalFlameAdd + koontzAdd + joshTandyAdd
                    numRavenDollars += toAdd
                    numTotalRavenDollars += toAdd
                    findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
                    handler.postDelayed(this, 1000)
                }
            },
            1000
        )
    }

    private fun calcCost(numOwned: Int, baseCost: Int, baseCostMultiplier: Double): Int {
        val cost = if (numOwned == 0) { baseCost } else { (baseCost * baseCostMultiplier * numOwned).toInt() }
        return if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
            1
        } else {
            0
        }
    }
    fun buyRodney(view: View) {
        numTotalRodneyUpgrades += calcCost(numTotalRodneyUpgrades, 10, 1.15)
    }
    fun buyHelios(view: View) {
        numTotalHeliosUpgrades += calcCost(numTotalHeliosUpgrades, 100, 1.15)
    }
    fun buyEternalFlame(view: View) {
        numTotalEternalFlameUpgrades += calcCost(numTotalEternalFlameUpgrades, 500, 1.15)
    }
    fun buyKoontz(view: View) {
        numTotalKoontzUpgrades += calcCost(numTotalKoontzUpgrades, 1000, 1.15)
    }
    fun buyJoshTandy(view: View) {
        numTotalJoshTandyUpgrades += calcCost(numTotalJoshTandyUpgrades, 2000, 1.15)
    }
    fun buyRodneyMultiplier(view: View) {
        numTotalRodneyMultipliers += calcCost(numTotalRodneyMultipliers - 1, 100, 1.5)
    }
    fun buyHeliosMultiplier(view: View) {
        numTotalHeliosMultipliers += calcCost(numTotalHeliosMultipliers - 1, 500, 1.5)
    }
    fun buyEternalFlameMultiplier(view: View) {
        numTotalEternalFlameMultipliers += calcCost(numTotalEternalFlameMultipliers - 1, 750, 1.5)
    }
    fun buyKoontzMultiplier(view: View) {
        numTotalKoontzMultipliers += calcCost(numTotalKoontzMultipliers - 1, 1250, 1.5)
    }
    fun buyJoshTandyMultiplier(view: View) {
        numTotalJoshTandyMultipliers += calcCost(numTotalJoshTandyMultipliers - 1, 2250, 1.5)
    }
}

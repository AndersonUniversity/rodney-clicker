package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    private var numRavenDollars = 0
    private var numTotalRavenDollars = 0
    private var numTotalRodneyUpgrades = 0
    private var numTotalRodneyMultipliers = 0
    private var rodneyCost = 10
    private var rodneyMilestone = 25
    private var numTotalHeliosUpgrades = 0
    private var numTotalHeliosMultipliers = 0
    private var heliosCost = 100
    private var heliosMilestone = 25

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
        rodneyCost = sharedPref.getString("Rodney_Cost", "10")?.toInt() ?: 10
        rodneyMilestone = sharedPref.getString("Rodney_Milestone", "25")?.toInt() ?: 25
        numTotalHeliosUpgrades = sharedPref.getString("Helios_Clickers", "0")?.toInt() ?: 0
        numTotalHeliosMultipliers = sharedPref.getString("Helios_Multipliers", "0")?.toInt() ?: 0
        heliosCost = sharedPref.getString("Helios_Cost", "100")?.toInt() ?: 100
        heliosMilestone = sharedPref.getString("Helios_Milestone", "25")?.toInt() ?: 25

        findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
        findViewById<TextView>(R.id.rodney_cost_text).text = FormatNum.formatNumber(rodneyCost.toLong())
        findViewById<TextView>(R.id.helios_cost_text).text = FormatNum.formatNumber(heliosCost.toLong())
        findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.GONE

        if (numTotalRodneyUpgrades >= rodneyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.VISIBLE
        }
        if (numTotalHeliosUpgrades >= heliosMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.VISIBLE
        }


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
            putString("Rodney_Cost", rodneyCost.toString())
            putString("Helios_Cost", heliosCost.toString())
            putString("Rodney_Milestone", rodneyMilestone.toString())
            putString("Helios_Milestone", heliosMilestone.toString())
        }.apply()
        startActivity(i)
    }

    val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
    val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)

    private fun gameLoop(view: StoreActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    ClickersAndUpgrades.addClicker(numTotalRodneyUpgrades, numTotalHeliosUpgrades, rodney, helios)
                    ClickersAndUpgrades.addMultiplier(numTotalRodneyMultipliers, numTotalHeliosMultipliers, rodney, helios)
                    val toAdd = (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier)
                    numRavenDollars += toAdd
                    numTotalRavenDollars += toAdd
                    findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
                    handler.postDelayed(this, 1000)
                }
            },
            1000
        )
    }

    private fun setCost(numOwned: Int, baseCost: Int, baseCostMultiplier: Double): Int {
        return if (numOwned == 0) { baseCost } else { (baseCost * baseCostMultiplier * numOwned).toInt() }
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
        rodneyCost = setCost(numTotalRodneyUpgrades, 10, 1.15)
        findViewById<TextView>(R.id.rodney_cost_text).text = FormatNum.formatNumber(rodneyCost.toLong())
        if (numTotalRodneyUpgrades >= rodneyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.VISIBLE
        }
    }

    fun buyHelios(view: View) {
        numTotalHeliosUpgrades += calcCost(numTotalHeliosUpgrades, 100, 1.15)
        heliosCost = setCost(numTotalHeliosUpgrades, 100, 1.15)
        findViewById<TextView>(R.id.helios_cost_text).text = FormatNum.formatNumber(heliosCost.toLong())
        if (numTotalHeliosUpgrades >= heliosMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.VISIBLE
        }
    }

    fun buyRodneyMultiplier(view: View) {
        numTotalRodneyMultipliers += calcCost(numTotalRodneyMultipliers - 1, 100, 1.5)
        rodneyMilestone *= 2
    }

    fun buyHeliosMultiplier(view: View) {
        numTotalHeliosMultipliers += calcCost(numTotalHeliosMultipliers - 1, 500, 1.5)
        heliosMilestone *= 2
    }
}

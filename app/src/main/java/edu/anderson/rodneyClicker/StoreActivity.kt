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
    private var numTotalClickerUpgrades = 0
    private var numTotalRodneyUpgrades = 0
    private var numTotalRodneyMultipliers = 0
    private var rodneyCost = 10
    private var rodneyMilestone = 25
    private var numTotalHeliosUpgrades = 0
    private var numTotalHeliosMultipliers = 0

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
        numTotalClickerUpgrades = numTotalRodneyUpgrades + numTotalHeliosUpgrades

        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
        findViewById<TextView>(R.id.rodney_cost_text).text = String.format("$%d", rodneyCost)
        findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.GONE
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
                    findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
                    handler.postDelayed(this, 1000)
                }
            },
            1000
        )
    }

    fun buyRodney(view: View) {
        val cost = (rodneyCost * 1.15).toInt()
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numTotalRodneyUpgrades += 1
            rodneyCost = cost
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
        findViewById<TextView>(R.id.rodney_cost_text).text = String.format("$%d", rodneyCost)

        if (numTotalRodneyUpgrades >= rodneyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.VISIBLE
        }
    }

    fun buyHelios(view: View) {
        val cost = if (numTotalHeliosUpgrades == 0) { 100 } else { (100 * 1.15 * numTotalHeliosUpgrades).toInt() }
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numTotalHeliosUpgrades += 1
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }

    fun buyRodneyMultiplier(view: View) {
        val cost = if (numTotalRodneyMultipliers == 1) { 100 } else { (100 * 1.5 * numTotalRodneyMultipliers).toInt() }
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numTotalRodneyMultipliers += 1
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
        rodneyMilestone += rodneyMilestone
    }

    fun buyHeliosMultiplier(view: View) {
        val cost = if (numTotalHeliosMultipliers == 1) { 500 } else { (500 * 1.5 * numTotalHeliosMultipliers).toInt() }
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numTotalHeliosMultipliers += 1
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }
}

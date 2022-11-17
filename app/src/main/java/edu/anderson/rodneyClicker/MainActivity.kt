package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var numRavenDollars = 0
    var totalRavenDollars = 0
    var numRodneyUpgrades = 0
    var numRodneyMultipliers = 0
    var numHeliosUpgrades = 0
    var numHeliosMultipliers = 0
    var totalClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**Saves all the information whenever the main activity is paused*/
    override fun onPause() {
        super.onPause()
        saveData()
    }

    /**Loads all the information whenever the main activity is started*/
    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        addClicker(this)
        addMultiplier(this)
        gameLoop(this)
    }

    class AutoClicker(var dps: Int, var numOwned: Int, var multiplier: Int) {
        fun buy() {
            numOwned += 1
        }
        fun buyMultiplier() {
            multiplier += 1
        }
    }

    val rodney = AutoClicker(1, 0, 1)
    val helios = AutoClicker(5, 0, 1)

    /** Called when the user taps the Store button */
    fun openStorePage(view: View) {
        val intent = Intent(this@MainActivity, StoreActivity::class.java)
        startActivity(intent)
    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<TextView>(R.id.ravenDollars)
        numRavenDollars += 1
        totalRavenDollars += 1
        totalClicks++
        val newRavenDollars = "R$$numRavenDollars"
        ravenDollars.text = newRavenDollars
        findViewById<TextView>(R.id.totalRavenDollars).text = totalRavenDollars.toString()
    }

    private fun addClicker(view: MainActivity) {
        val newRodneys = numRodneyUpgrades - rodney.numOwned
        val newHelios = numHeliosUpgrades - helios.numOwned
        for (i in 1..newRodneys) {
            rodney.buy()
        }
        for (i in 1..newHelios) {
            helios.buy()
        }
        val rodneysOwned = findViewById<TextView>(R.id.total_rodneys)
        val heliosOwned = findViewById<TextView>(R.id.total_helios)

        rodneysOwned.text = "Total Rodneys: " + rodney.numOwned.toString()
        heliosOwned.text = "Total Helios: " + helios.numOwned.toString()
    }

    /**Adds the multipliers to the clickers*/
    private fun addMultiplier(view: MainActivity) {
        val newRodneyMultipliers = numRodneyMultipliers - rodney.multiplier
        val newHeliosMultipliers = numHeliosMultipliers - helios.multiplier
        for (i in 1..newRodneyMultipliers) {
            rodney.buyMultiplier()
        }
        for (i in 1..newHeliosMultipliers) {
            helios.buyMultiplier()
        }
    }

    /**run every second to add dps*/
    private fun gameLoop(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    showRDPS()
                    val ravenDollars = findViewById<TextView>(R.id.ravenDollars)
                    val totalRD = findViewById<TextView>(R.id.totalRavenDollars)
                    val toAdd = (rodney.dps * rodney.numOwned * rodney.multiplier) + (helios.dps * helios.numOwned * helios.multiplier)
                    numRavenDollars += toAdd
                    totalRavenDollars += toAdd
                    ravenDollars.text = "R$$numRavenDollars"
                    totalRD.text = "$totalRavenDollars"
                    findViewById<TextView>(R.id.achievementPopup).text = checkAchievements(totalRavenDollars, totalClicks)
                    handler.postDelayed(this, 1000)
                }
            },
            1000
        )
    }

    /** Save Data */
    private fun saveData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", numRavenDollars.toString())
            putString("Total_Raven_Dollars", totalRavenDollars.toString())
            putString("Rodney_Clickers", rodney.numOwned.toString())
            putString("Rodney_Multipliers", rodney.multiplier.toString())
            putString("Helios_Clickers", helios.numOwned.toString())
            putString("Helios_Multipliers", helios.multiplier.toString())
        }.apply()
    }

    /** Load Data */
    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedRavenDollars = sharedPref.getString("Raven_Dollars", "0")
        val allRavenDollars = sharedPref.getString("Total_Raven_Dollars", "0")
        val savedRodneyClickers = sharedPref.getString("Rodney_Clickers", "0")
        val savedRodneyClickersMultipliers = sharedPref.getString("Rodney_Multipliers", "0")
        val savedHeliosClickers = sharedPref.getString("Helios_Clickers", "0")
        val savedHeliosClickersMultipliers = sharedPref.getString("Helios_Multipliers", "0")

        if (savedRavenDollars != null) {
            findViewById<TextView>(R.id.ravenDollars).text = "R$$savedRavenDollars"
            numRavenDollars = savedRavenDollars.toInt()
        }
        if (allRavenDollars != null) {
            findViewById<TextView>(R.id.totalRavenDollars).text = "$allRavenDollars"
            totalRavenDollars = allRavenDollars.toInt()
        }
        if (savedRodneyClickers != null) {
            findViewById<TextView>(R.id.total_rodneys).text = "Total Rodneys: $savedRodneyClickers"
            rodney.numOwned = savedRodneyClickers.toInt()
            numRodneyUpgrades = savedRodneyClickers.toInt()
        }
        if (savedRodneyClickersMultipliers != null) {
            numRodneyMultipliers = savedRodneyClickersMultipliers.toInt()
        }
        if (savedHeliosClickers != null) {
            findViewById<TextView>(R.id.total_helios).text = "Total Helioses: $savedHeliosClickers"
            helios.numOwned = savedHeliosClickers.toInt()
            numHeliosUpgrades = savedHeliosClickers.toInt()
        }
        if (savedHeliosClickersMultipliers != null) {
            numHeliosMultipliers = savedHeliosClickersMultipliers.toInt()
        }
    }
    private fun showRDPS() {
        val totalRodney = rodney.dps * rodney.numOwned * rodney.multiplier
        val totalHelios = helios.dps * helios.numOwned * helios.multiplier
        val viewText = findViewById<TextView>(R.id.ravenDollarsPerSecond)
        val currRDPS = (totalRodney + totalHelios).toString()
        val displayText = "Raven Dollars Per Second: $currRDPS"
        viewText.text = displayText
    }
}

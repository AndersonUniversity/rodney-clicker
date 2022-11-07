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
    var numClickerUpgrades = 0
    var numRodneyMultipliers = 0

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
        val numNewClickerUpgrades = numClickerUpgrades - rodney.numOwned
        if (numNewClickerUpgrades > 0) {
            addClicker(this, numNewClickerUpgrades)
        }
        val numNewClickerMultipliers = numRodneyMultipliers - rodney.multiplier
        if (numNewClickerMultipliers > 0) {
            addMultiplier(this, numNewClickerMultipliers)
        }
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

    /** Called when the user taps the Store button */
    fun openStorePage(view: View) {
        val intent = Intent(this@MainActivity, StoreActivity::class.java)
        startActivity(intent)
    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<TextView>(R.id.ravenDollars)
        numRavenDollars += 1
        val newRavenDollars = "R$$numRavenDollars"
        ravenDollars.setText(newRavenDollars)
    }

    private fun addClicker(view: MainActivity, newClickers: Int) {
        for (i in 1..newClickers) {
            rodney.buy()
        }
        val rodneysOwned = findViewById<TextView>(R.id.total_rodneys)
        rodneysOwned.setText("Total Rodneys: " + rodney.numOwned.toString())
    }

    private fun addMultiplier(view: MainActivity, newMultipliers: Int) {
        for (i in 1..newMultipliers) {
            rodney.buyMultiplier()
        }
    }

    /**run every second to add dps*/
    private fun gameLoop(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    showRDPS(numClickerUpgrades, numRodneyMultipliers)
                    val ravenDollars = findViewById<TextView>(R.id.ravenDollars)
                    numRavenDollars += (rodney.dps * rodney.numOwned * rodney.multiplier)
                    ravenDollars.setText("R$$numRavenDollars")
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
            putString("Rodney_Clickers", rodney.numOwned.toString())
            putString("Rodney_Multipliers", rodney.multiplier.toString())
        }.apply()
    }

    /** Load Data */
    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedRavenDollars = sharedPref.getString("Raven_Dollars", "0")
        val savedRodneyClickers = sharedPref.getString("Rodney_Clickers", "0")
        val savedRodneyClickersMultipliers = sharedPref.getString("Rodney_Multipliers", "0")

        if (savedRavenDollars != null) {
            findViewById<TextView>(R.id.ravenDollars).setText("R$$savedRavenDollars")
            numRavenDollars = savedRavenDollars.toInt()
        }
        if (savedRodneyClickers != null) {
            findViewById<TextView>(R.id.total_rodneys).setText("Total Rodneys: $savedRodneyClickers")
            rodney.numOwned = savedRodneyClickers.toInt()
            numClickerUpgrades = savedRodneyClickers.toInt()
        }
        if (savedRodneyClickersMultipliers != null) {
            numRodneyMultipliers = savedRodneyClickersMultipliers.toInt()
        }
    }
    private fun showRDPS(clickers: Int, multipliers: Int) {
        val viewText = findViewById<TextView>(R.id.ravenDollarsPerSecond)
        val currRDPS = (clickers * multipliers).toString()
        val displayText = "Raven Dollars Per Second: $currRDPS"
        viewText.text = displayText
    }
}

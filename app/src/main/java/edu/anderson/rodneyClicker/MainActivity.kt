package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var numRavenDollars = 0
    var numClickerUpgrades = 0
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
        numRavenDollars = intent.extras?.getInt("ravenDollarsKey", 0) ?: 0
        val updatedNumClickerUpgrades = intent.extras?.getInt("clickerUpgradesKey", 0) ?: 0
        if (updatedNumClickerUpgrades > numClickerUpgrades) {
            addClicker(this, updatedNumClickerUpgrades - numClickerUpgrades)
            numClickerUpgrades = updatedNumClickerUpgrades
        }
        gameLoop(this)
    }

    class AutoClicker(var dps: Int, var numOwned: Int) {
        fun buy() {
            numOwned += 1
        }
    }

    val rodney = AutoClicker(1, 0)

    /** Called when the user taps the Store button */
    fun openStorePage(view: View) {
        val intent = Intent(this, StoreActivity::class.java)
        intent.putExtra("clickerUpgradesKey", numClickerUpgrades)
        intent.putExtra("ravenDollarsKey", findViewById<EditText>(R.id.ravenDollars).text.toString().toInt())
        startActivity(intent)
    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
        val currNum = ravenDollars.text.toString().toInt()
        ravenDollars.setText((currNum + 1).toString())
        numRavenDollars += 1
    }

    fun addClicker(view: MainActivity, newClickers: Int) {
        for (i in 1..newClickers) {
            rodney.buy()
        }
        val rodneysOwned = findViewById<EditText>(R.id.total_rodneys)
        rodneysOwned.setText("Total Rodneys: " + rodney.numOwned.toString())
    }

    /**run every second to add dps*/
    fun gameLoop(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
                    val currNum = ravenDollars.text.toString().toInt()
                    numRavenDollars += (rodney.dps * rodney.numOwned)
                    ravenDollars.setText((currNum + (rodney.dps * rodney.numOwned)).toString())
                    showRDPS(numRavenDollars)
                    saveData()
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
        }.apply()
    }

    /** Load Data */
    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedRavenDollars = sharedPref.getString("Raven_Dollars", null)
        val savedRodneyClickers = sharedPref.getString("Rodney_Clickers", null)

        if (savedRavenDollars != null) {
            findViewById<EditText>(R.id.ravenDollars).setText(savedRavenDollars)
            numRavenDollars = savedRavenDollars.toInt()
        }
        if (savedRodneyClickers != null) {
            findViewById<EditText>(R.id.total_rodneys).setText("Total Rodneys: $savedRodneyClickers")
            rodney.numOwned = savedRodneyClickers.toInt()
        }
    }
    private fun showRDPS(cash: Int) {
        val viewText = findViewById<TextView>(R.id.ravenDollarsPerSecond)
        val currRDPS = cash.toString()
        val displayText = "Raven Dollars Per Second: $currRDPS"
        viewText.text = displayText
    }
}

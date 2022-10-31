package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
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

    override fun onResume() {
        super.onResume()
        loadData()
        numClickerUpgrades = intent.extras?.getInt("key", 0) ?: 0
        if (numClickerUpgrades > 0) {
            // buy rodney for each number
            for (i in 1..numClickerUpgrades) {
                addClicker(this)
            }
        }
        addFunds(this)
    }

    class AutoClicker(var dps: Int, var cost: Int, var numOwned: Int) {
        fun buy() {
            var multiplier = 1.5
            numOwned += 1
            cost = (cost * multiplier).toInt()
        }
    }

    val rodney = AutoClicker(1, 10, 0)

    /** Called when the user taps the Store button */
    fun openStorePage(view: View) {
        val intent = Intent(this, StoreActivity::class.java)
        startActivity(intent)
    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
        val currNum = ravenDollars.text.toString().toInt()
        ravenDollars.setText((currNum + 1).toString())
    }

    fun addClicker(view: MainActivity) {
        val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
        val currNum = ravenDollars.text.toString().toInt()
        if (currNum >= rodney.cost) {
            ravenDollars.setText((currNum - rodney.cost).toString())
            rodney.buy()
            val rodneysOwned = findViewById<EditText>(R.id.total_rodneys)
            rodneysOwned.setText("Total Rodneys: " + rodney.numOwned.toString())
        }
    }

    /**run every second to add dps*/
    fun addFunds(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
                    val currNum = ravenDollars.text.toString().toInt()
                    ravenDollars.setText((currNum + (rodney.dps * rodney.numOwned)).toString())
                    handler.postDelayed(this, 1000)
                }
            },
            1000
        )
    }

    /** Save Data */
    private fun saveData() {
        val currNum = findViewById<EditText>(R.id.ravenDollars).text.toString()

        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", currNum)
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
        }
        if (savedRodneyClickers != null) {
            findViewById<EditText>(R.id.total_rodneys).setText("Total Rodneys: $savedRodneyClickers")
        }
    }
}

package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    private var numRavenDollars = 0
    private var numTotalClickerUpgrades = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        numRavenDollars = sharedPref.getString("Raven_Dollars", "0")?.toInt() ?: 0
        numTotalClickerUpgrades = sharedPref.getString("Rodney_Clickers", "0")?.toInt() ?: 0

        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        val i = Intent(this@StoreActivity, MainActivity::class.java)
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", numRavenDollars.toString())
            putString("Rodney_Clickers", numTotalClickerUpgrades.toString())
        }.apply()
        startActivity(i)
    }

    fun buyRodney(view: View) {
        val cost = if (numTotalClickerUpgrades == 0) { 10 } else { (10 * 1.15 * numTotalClickerUpgrades).toInt() }
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numTotalClickerUpgrades += 1
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }
}

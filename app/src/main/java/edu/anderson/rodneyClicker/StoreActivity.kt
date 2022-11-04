package edu.anderson.rodneyClicker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    private var numRavenDollars = 0
    private var numClickerUpgrades = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    override fun onResume() {
        super.onResume()
        numRavenDollars = intent.extras?.getInt("ravenDollarsKey", 0) ?: 0
        numClickerUpgrades = intent.extras?.getInt("clickerUpgradesKey", 0) ?: 0

        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        val i = Intent(this@StoreActivity, MainActivity::class.java)
        i.putExtra("clickerUpgradesKey", numClickerUpgrades)
        i.putExtra("ravenDollarsKey", numRavenDollars)
        startActivity(i)
    }

    fun buyRodney(view: View) {
        val cost = if (numClickerUpgrades == 0) { 10 } else { (10 * 1.15 * numClickerUpgrades).toInt() }
        if (numRavenDollars >= cost) {
            numRavenDollars -= cost
            numClickerUpgrades += 1
        }
        findViewById<TextView>(R.id.currRavenDollars).text = String.format("Raven Dollars: %d", numRavenDollars)
    }
}

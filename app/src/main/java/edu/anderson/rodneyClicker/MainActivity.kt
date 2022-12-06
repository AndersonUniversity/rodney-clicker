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
    var numEternalFlameUpgrades = 0
    var numEternalFlameMultipliers = 0
    var numKoontzUpgrades = 0
    var numKoontzMultipliers = 0
    var numJoshTandyUpgrades = 0
    var numJoshTandyMultipliers = 0
    var gameLoopRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**Saves all the information whenever the main activity is paused*/
    override fun onPause() {
        super.onPause()
        saveData()
        gameLoopRunning = false
    }

    /**Loads all the information whenever the main activity is started*/
    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        updateClicker(this)
        updateMultiplier(this)
        showRDPS()
        setAchievementView("")
        gameLoopRunning = true
        gameLoop(this)
    }

    /** Called when the user taps the Store button */
    fun openStorePage(view: View) {
        val intent = Intent(this@MainActivity, StoreActivity::class.java)
        startActivity(intent)
    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<TextView>(R.id.ravenDollars)
        val totalRavens = findViewById<TextView>(R.id.totalRavenDollars)
        numRavenDollars += 1
        totalRavenDollars += 1
        totalClicks++
        val newRavenDollars = FormatNum.formatNumber(numRavenDollars.toLong())
        val newTotalRavenDollars = FormatNum.formatNumberTRD((totalRavenDollars.toLong()))
        ravenDollars.text = newRavenDollars
        totalRavens.text = newTotalRavenDollars
        totalRavens.text = String.format(newTotalRavenDollars)
    }

    val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
    val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)
    val eternalFlame = ClickersAndUpgrades.AutoClicker(10, 0, 1)
    val koontz = ClickersAndUpgrades.AutoClicker(15, 0, 1)
    val joshTandy = ClickersAndUpgrades.AutoClicker(20, 0, 1)

    private fun updateClicker(view: MainActivity) {
        ClickersAndUpgrades.addClicker(numRodneyUpgrades, numHeliosUpgrades, numEternalFlameUpgrades, numKoontzUpgrades, numJoshTandyUpgrades, rodney, helios, eternalFlame, koontz, joshTandy)
        findViewById<TextView>(R.id.total_rodneys).text = String.format("Total Rodneys: %d", rodney.numOwned)
        findViewById<TextView>(R.id.total_helios).text = String.format("Total Helios: %d", helios.numOwned)
        findViewById<TextView>(R.id.total_eternalFlame).text = String.format("Total Eternal Flames: %d", eternalFlame.numOwned)
        findViewById<TextView>(R.id.total_koontz).text = String.format("Total Koontz: %d", koontz.numOwned)
        findViewById<TextView>(R.id.total_joshTandy).text = String.format("Total Josh Tandy: %d", joshTandy.numOwned)
    }

    /**Adds the multipliers to the clickers*/
    private fun updateMultiplier(view: MainActivity) {
        ClickersAndUpgrades.addMultiplier(numRodneyMultipliers, numHeliosMultipliers, numEternalFlameMultipliers, numKoontzMultipliers, numJoshTandyMultipliers, rodney, helios, eternalFlame, koontz, joshTandy)
    }

    /**run every second to add dps*/
    private fun gameLoop(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (!gameLoopRunning) { return }

                    val rodneyAdd = (rodney.dps * rodney.numOwned * rodney.multiplier)
                    val heliosAdd = (helios.dps * helios.numOwned * helios.multiplier)
                    val eternalFlameAdd = (eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier)
                    val koontzAdd = (koontz.dps * koontz.numOwned * koontz.multiplier)
                    val joshTandyAdd = (joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier)
                    val toAdd = rodneyAdd + heliosAdd + eternalFlameAdd + koontzAdd + joshTandyAdd
                    numRavenDollars += toAdd
                    totalRavenDollars += toAdd
                    findViewById<TextView>(R.id.ravenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
                    findViewById<TextView>(R.id.totalRavenDollars).text = FormatNum.formatNumberTRD((totalRavenDollars.toLong()))
                    val currAchievement = checkAchievements(totalRavenDollars, totalClicks, numRodneyUpgrades, numHeliosUpgrades, numEternalFlameUpgrades, numKoontzUpgrades, numJoshTandyUpgrades)
                    setAchievementView(currAchievement)
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
            putString("Total_Clicks", totalClicks.toString())
            putString("Completed_Achievements_String", completedAchievements.toString())
            putString("Eternal_Flame_Clickers", eternalFlame.numOwned.toString())
            putString("Eternal_Flame_Multipliers", eternalFlame.multiplier.toString())
            putString("Koontz_Clickers", koontz.numOwned.toString())
            putString("Koontz_Multiplier", koontz.multiplier.toString())
            putString("Josh_Tandy_Clickers", joshTandy.numOwned.toString())
            putString("Josh_Tandy_Multiplier", joshTandy.multiplier.toString())
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
        val savedClicks = sharedPref.getString("Total_Clicks", "0")
        val savedAchievementString = sharedPref.getString("Completed_Achievements_String", "")
        val savedEternalFlameClickers = sharedPref.getString("Eternal_Flame_Clickers", "0")
        val savedEternalFlameClickersMultipliers = sharedPref.getString("Eternal_Flame_Multiplier", "0")
        val savedKoontzClickers = sharedPref.getString("Koontz_Clickers", "0")
        val savedKoontzClickersMultipliers = sharedPref.getString("Koontz_Multipliers", "0")
        val savedJoshTandyClickers = sharedPref.getString("Josh_Tandy_Clickers", "0")
        val savedJoshTandyClickersMultipliers = sharedPref.getString("Josh_Tandy_Multipliers", "0")

        if (savedAchievementString != null) {
            completedAchievements = savedAchievementString
        }
        if (savedRavenDollars != null) {
            findViewById<TextView>(R.id.ravenDollars).text = FormatNum.formatNumber(savedRavenDollars.toLong())
            numRavenDollars = savedRavenDollars.toInt()
        }
        if (savedClicks != null) {
            totalClicks = savedClicks.toInt()
        }
        if (allRavenDollars != null) {
            findViewById<TextView>(R.id.totalRavenDollars).text = FormatNum.formatNumberTRD((allRavenDollars.toLong()))
            totalRavenDollars = allRavenDollars.toInt()
        }
        if (savedRodneyClickers != null) {
            findViewById<TextView>(R.id.total_rodneys).text = String.format("Total Rodneys: %s", savedRodneyClickers)
            rodney.numOwned = savedRodneyClickers.toInt()
            numRodneyUpgrades = savedRodneyClickers.toInt()
        }
        if (savedRodneyClickersMultipliers != null) {
            numRodneyMultipliers = savedRodneyClickersMultipliers.toInt()
        }
        if (savedHeliosClickers != null) {
            findViewById<TextView>(R.id.total_helios).text = String.format("Total Helios: %s", savedHeliosClickers)
            helios.numOwned = savedHeliosClickers.toInt()
            numHeliosUpgrades = savedHeliosClickers.toInt()
        }
        if (savedHeliosClickersMultipliers != null) {
            numHeliosMultipliers = savedHeliosClickersMultipliers.toInt()
        }
        if (savedEternalFlameClickers != null) {
            findViewById<TextView>(R.id.total_eternalFlame).text = String.format("Total Eternal Flame: %s", savedEternalFlameClickers)
            eternalFlame.numOwned = savedEternalFlameClickers.toInt()
            numEternalFlameUpgrades = savedEternalFlameClickers.toInt()
        }
        if (savedEternalFlameClickersMultipliers != null) {
            numEternalFlameMultipliers = savedEternalFlameClickersMultipliers.toInt()
        }
        if (savedKoontzClickers != null) {
            findViewById<TextView>(R.id.total_koontz).text = String.format("Total Koontz: %s", savedKoontzClickers)
            koontz.numOwned = savedKoontzClickers.toInt()
            numKoontzUpgrades = savedKoontzClickers.toInt()
        }
        if (savedKoontzClickersMultipliers != null) {
            numKoontzMultipliers = savedKoontzClickersMultipliers.toInt()
        }
        if (savedJoshTandyClickers != null) {
            findViewById<TextView>(R.id.total_joshTandy).text = String.format("Total Josh Tandy: %s", savedJoshTandyClickers)
            joshTandy.numOwned = savedJoshTandyClickers.toInt()
            numJoshTandyUpgrades = savedJoshTandyClickers.toInt()
        }
        if (savedJoshTandyClickersMultipliers != null) {
            numJoshTandyMultipliers = savedJoshTandyClickersMultipliers.toInt()
        }
    }
    private fun showRDPS() {
        val totalRodney = rodney.dps * rodney.numOwned * rodney.multiplier
        val totalHelios = helios.dps * helios.numOwned * helios.multiplier
        val totalEternalFlame = eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier
        val totalKoontz = koontz.dps * koontz.numOwned * koontz.multiplier
        val totalJoshTandy = joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier
        val viewText = findViewById<TextView>(R.id.ravenDollarsPerSecond)
        val currRDPS = (totalRodney + totalHelios + totalEternalFlame + totalKoontz + totalJoshTandy).toString()
        val formattedRDPS = FormatNum.formatNumber(currRDPS.toLong())
        val displayText = "Raven Dollars Per Second: " + formattedRDPS.substring(2, formattedRDPS.length)
        viewText.text = displayText
    }
    private fun setAchievementView(text: String) {
        val popup = findViewById<TextView>(R.id.achievementPopup)
        popup.visibility = View.INVISIBLE
        if (text != "") {
            popup.text = text
            popup.visibility = View.VISIBLE
        }
    }
}

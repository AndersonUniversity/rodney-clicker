package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.marginLeft

class MainActivity : AppCompatActivity() {
    var numRavenDollars = 0L
    var totalRavenDollars = 0L
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
    var achievementCount = 0

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
        updateClicker(this)
        updateMultiplier(this)
        showRDPS()
        setAchievementView("")
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
        totalRavens.text = newTotalRavenDollars
    }

    val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
    val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)
    val eternalFlame = ClickersAndUpgrades.AutoClicker(10, 0, 1)
    val koontz = ClickersAndUpgrades.AutoClicker(15, 0, 1)
    val joshTandy = ClickersAndUpgrades.AutoClicker(20, 0, 1)

    private fun updateClicker(view: MainActivity) {
        ClickersAndUpgrades.addClicker(numRodneyUpgrades, numHeliosUpgrades, numEternalFlameUpgrades, numKoontzUpgrades, numJoshTandyUpgrades, rodney, helios, eternalFlame, koontz, joshTandy)
        findViewById<TextView>(R.id.total_rodneys).text = String.format(rodney.numOwned.toString())
        findViewById<TextView>(R.id.total_helios).text = String.format(helios.numOwned.toString())
        findViewById<TextView>(R.id.total_eternalFlame).text = String.format(eternalFlame.numOwned.toString())
        findViewById<TextView>(R.id.total_koontz).text = String.format(koontz.numOwned.toString())
        findViewById<TextView>(R.id.total_joshTandy).text = String.format(joshTandy.numOwned.toString())
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
                    findViewById<TextView>(R.id.ravenDollars).text = FormatNum.formatNumber(numRavenDollars.toLong())
                    findViewById<TextView>(R.id.totalRavenDollars).text = FormatNum.formatNumberTRD((totalRavenDollars.toLong()))
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
            putString("Completed_Achievements_String", completedAchievements)
            putString("Eternal_Flame_Clickers", eternalFlame.numOwned.toString())
            putString("Eternal_Flame_Multipliers", eternalFlame.multiplier.toString())
            putString("Koontz_Clickers", koontz.numOwned.toString())
            putString("Koontz_Multiplier", koontz.multiplier.toString())
            putString("Josh_Tandy_Clickers", joshTandy.numOwned.toString())
            putString("Josh_Tandy_Multiplier", joshTandy.multiplier.toString())
            putString("Achievement_Count", achievementCount.toString())
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
        val savedAchievementCount = sharedPref.getString("Achievement_Count", "0")

        if (savedAchievementCount != null) {
            achievementCount = savedAchievementCount.toInt()
        }
        if (savedAchievementString != null) {
            completedAchievements = savedAchievementString
        }
        if (savedRavenDollars != null) {
            findViewById<TextView>(R.id.ravenDollars).text = FormatNum.formatNumber(savedRavenDollars.toLong())
            numRavenDollars = savedRavenDollars.toLong()
        }
        if (savedClicks != null) {
            totalClicks = savedClicks.toInt()
        }
        if (allRavenDollars != null) {
            findViewById<TextView>(R.id.totalRavenDollars).text = FormatNum.formatNumberTRD((allRavenDollars.toLong()))
            totalRavenDollars = allRavenDollars.toLong()
        }
        if (savedRodneyClickers != null) {
            findViewById<TextView>(R.id.total_rodneys).text = "$savedRodneyClickers"
            rodney.numOwned = savedRodneyClickers.toInt()
            numRodneyUpgrades = savedRodneyClickers.toInt()
        }
        if (savedRodneyClickersMultipliers != null) {
            numRodneyMultipliers = savedRodneyClickersMultipliers.toInt()
        }
        if (savedHeliosClickers != null) {
            findViewById<TextView>(R.id.total_helios).text = "$savedHeliosClickers"
            helios.numOwned = savedHeliosClickers.toInt()
            numHeliosUpgrades = savedHeliosClickers.toInt()
        }
        if (savedHeliosClickersMultipliers != null) {
            numHeliosMultipliers = savedHeliosClickersMultipliers.toInt()
        }
        if (savedEternalFlameClickers != null) {
            findViewById<TextView>(R.id.total_eternalFlame).text = String.format(savedEternalFlameClickers)
            eternalFlame.numOwned = savedEternalFlameClickers.toInt()
            numEternalFlameUpgrades = savedEternalFlameClickers.toInt()
        }
        if (savedEternalFlameClickersMultipliers != null) {
            numEternalFlameMultipliers = savedEternalFlameClickersMultipliers.toInt()
        }
        if (savedKoontzClickers != null) {
            findViewById<TextView>(R.id.total_koontz).text = String.format(savedKoontzClickers)
            koontz.numOwned = savedKoontzClickers.toInt()
            numKoontzUpgrades = savedKoontzClickers.toInt()
        }
        if (savedKoontzClickersMultipliers != null) {
            numKoontzMultipliers = savedKoontzClickersMultipliers.toInt()
        }
        if (savedJoshTandyClickers != null) {
            findViewById<TextView>(R.id.total_joshTandy).text = String.format(savedJoshTandyClickers)
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
        val displayText = formattedRDPS.substring(2, formattedRDPS.length) + " R$/s"
        viewText.text = displayText
    }
    private fun setAchievementView(text: String) {
        val popup = findViewById<TextView>(R.id.achievementPopup)
        popup.visibility = View.INVISIBLE
        if (text != "") {
            if(popup.text != text){
                achievementCount++
            }
            popup.text = text
            popup.visibility = View.VISIBLE
        }
        if(achievementCount >= 1){
            findViewById<ImageView>(R.id.achievement0).visibility = View.VISIBLE
        }
        if(achievementCount >= 2) {
            findViewById<ImageView>(R.id.achievement1).visibility = View.VISIBLE
        }
        if(achievementCount >= 3){
            findViewById<ImageView>(R.id.achievement2).visibility = View.VISIBLE
        }
        if(achievementCount >= 4){
            findViewById<ImageView>(R.id.achievement3).visibility = View.VISIBLE
        }
        if(achievementCount >= 5){
            findViewById<ImageView>(R.id.achievement4).visibility = View.VISIBLE
        }
        if(achievementCount >= 6){
            findViewById<ImageView>(R.id.achievement5).visibility = View.VISIBLE
        }
        if(achievementCount >= 7){
            findViewById<ImageView>(R.id.achievement6).visibility = View.VISIBLE
        }
        if(achievementCount >= 8){
            findViewById<ImageView>(R.id.achievement7).visibility = View.VISIBLE
        }
        if(achievementCount >= 9){
            findViewById<ImageView>(R.id.achievement8).visibility = View.VISIBLE
        }
        if(achievementCount >= 10){
            findViewById<ImageView>(R.id.achievement9).visibility = View.VISIBLE
        }

    }
}

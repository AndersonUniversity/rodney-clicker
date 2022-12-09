package edu.anderson.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    private var numRavenDollars = 0L
    private var numTotalRavenDollars = 0L
    private var numTotalRodneyUpgrades = 0
    private var numTotalRodneyMultipliers = 1
    private var rodneyCost = 10
    private var rodneyMilestone = 25
    private var numTotalHeliosUpgrades = 0
    private var numTotalHeliosMultipliers = 1
    private var heliosCost = 100
    private var heliosMilestone = 25
    private var numTotalEternalFlameUpgrades = 0
    private var numTotalEternalFlameMultipliers = 1
    private var eternalFlameCost = 500
    private var eternalFlameMilestone = 25
    private var numTotalKoontzUpgrades = 0
    private var numTotalKoontzMultipliers = 1
    private var koontzCost = 1000
    private var koontzMilestone = 25
    private var numTotalJoshTandyUpgrades = 0
    private var numTotalJoshTandyMultipliers = 1
    private var joshTandyCost = 2000
    private var joshTandyMilestone = 25
    var gameLoopRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    override fun onPause() {
        super.onPause()
        gameLoopRunning = false
    }

    override fun onResume() {
        super.onResume()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@StoreActivity, MainActivity::class.java)
                saveData()
                startActivity(intent)
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
        onBackPressedDispatcher.addCallback(callback)

        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        numRavenDollars = sharedPref.getString("Raven_Dollars", "0")?.toLong() ?: 0
        numTotalRavenDollars = sharedPref.getString("Total_Raven_Dollars", "0")?.toLong() ?: 0
        numTotalRodneyUpgrades = sharedPref.getString("Rodney_Clickers", "0")?.toInt() ?: 0
        numTotalRodneyMultipliers = sharedPref.getString("Rodney_Multipliers", "0")?.toInt() ?: 0
        numTotalHeliosUpgrades = sharedPref.getString("Helios_Clickers", "0")?.toInt() ?: 0
        numTotalHeliosMultipliers = sharedPref.getString("Helios_Multipliers", "0")?.toInt() ?: 0
        numTotalEternalFlameUpgrades = sharedPref.getString("Eternal_Flame_Clickers", "0")?.toInt() ?: 0
        numTotalEternalFlameMultipliers = sharedPref.getString("Eternal_Flame_Multipliers", "0")?.toInt() ?: 0
        numTotalKoontzUpgrades = sharedPref.getString("Koontz_Clickers", "0")?.toInt() ?: 0
        numTotalKoontzMultipliers = sharedPref.getString("Koontz_Multipliers", "0")?.toInt() ?: 0
        numTotalJoshTandyUpgrades = sharedPref.getString("Josh_Tandy_Clickers", "0")?.toInt() ?: 0
        numTotalJoshTandyMultipliers = sharedPref.getString("Josh_Tandy_Multipliers", "0")?.toInt() ?: 0
        achievementCount = sharedPref.getString("Achievement_Count", "0")?.toInt() ?: 0
        completedAchievements = sharedPref.getString("Completed_Achievements_String", "") ?: ""

        /**load saved cost and milestones*/
        loadData()

        findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars)
        findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars)
        findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars)
        findViewById<TextView>(R.id.rodney_cost_text).text = FormatNum.formatNumber(rodneyCost.toLong())
        findViewById<TextView>(R.id.helios_cost_text).text = FormatNum.formatNumber(heliosCost.toLong())
        findViewById<TextView>(R.id.eternalFlame_cost_text).text = FormatNum.formatNumber(eternalFlameCost.toLong())
        findViewById<TextView>(R.id.koontz_cost_text).text = FormatNum.formatNumber(koontzCost.toLong())
        findViewById<TextView>(R.id.joshTandy_cost_text).text = FormatNum.formatNumber(joshTandyCost.toLong())
        findViewById<TextView>(R.id.rodneyValue).text = FormatNum.formatNumberRDPS((numTotalRodneyMultipliers * rodney.dps).toLong())
        findViewById<TextView>(R.id.heliosValue).text = FormatNum.formatNumberRDPS((numTotalHeliosMultipliers * helios.dps).toLong())
        findViewById<TextView>(R.id.flameValue).text = FormatNum.formatNumberRDPS((numTotalEternalFlameMultipliers * eternalFlame.dps).toLong())
        findViewById<TextView>(R.id.koontzValue).text = FormatNum.formatNumberRDPS(((numTotalKoontzMultipliers + 1) * koontz.dps).toLong())
        findViewById<TextView>(R.id.tandyValue).text = FormatNum.formatNumberRDPS(((numTotalJoshTandyMultipliers + 1) * joshTandy.dps).toLong())
        findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_eternalFlame).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_koontz).visibility = View.GONE
        findViewById<ImageButton>(R.id.buy_multiplier_joshTandy).visibility = View.GONE

        if (numTotalRodneyUpgrades >= rodneyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.VISIBLE
        }
        if (numTotalHeliosUpgrades >= heliosMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.VISIBLE
        }
        if (numTotalEternalFlameUpgrades >= eternalFlameMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_eternalFlame).visibility = View.VISIBLE
        }
        if (numTotalKoontzUpgrades >= koontzMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_koontz).visibility = View.VISIBLE
        }
        if (numTotalJoshTandyUpgrades >= joshTandyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_joshTandy).visibility = View.VISIBLE
        }
        gameLoopRunning = true
        gameLoop(this)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        val i = Intent(this@StoreActivity, MainActivity::class.java)
        saveData()
        startActivity(i)
    }

    private fun saveData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", numRavenDollars.toString())
            putString("Total_Raven_Dollars", numTotalRavenDollars.toString())
            putString("Rodney_Clickers", numTotalRodneyUpgrades.toString())
            putString("Rodney_Multipliers", numTotalRodneyMultipliers.toString())
            putString("Helios_Clickers", numTotalHeliosUpgrades.toString())
            putString("Helios_Multipliers", numTotalHeliosMultipliers.toString())
            putString("Eternal_Flame_Clickers", numTotalEternalFlameUpgrades.toString())
            putString("Eternal_Flame_Multipliers", numTotalEternalFlameMultipliers.toString())
            putString("Koontz_Clickers", numTotalKoontzUpgrades.toString())
            putString("Koontz_Multipliers", numTotalKoontzMultipliers.toString())
            putString("Josh_Tandy_Clickers", numTotalJoshTandyUpgrades.toString())
            putString("Josh_Tandy_Multipliers", numTotalJoshTandyMultipliers.toString())
            putString("rodney_cost", rodneyCost.toString())
            putString("helios_cost", heliosCost.toString())
            putString("eternal_flame_cost", eternalFlameCost.toString())
            putString("koontz_cost", koontzCost.toString())
            putString("josh_tandy_cost", joshTandyCost.toString())
            putString("rodney_milestone", rodneyMilestone.toString())
            putString("helios_milestone", heliosMilestone.toString())
            putString("eternal_flame_milestone", eternalFlameMilestone.toString())
            putString("koontz_milestone", koontzMilestone.toString())
            putString("josh_tandy_milestone", joshTandyMilestone.toString())
            putString("Completed_Achievements_String", completedAchievements)
            putString("Achievement_Count", achievementCount.toString())
        }.apply()
    }

    val rodney = ClickersAndUpgrades.AutoClicker(1, 0, 1)
    val helios = ClickersAndUpgrades.AutoClicker(5, 0, 1)
    val eternalFlame = ClickersAndUpgrades.AutoClicker(15, 0, 1)
    val koontz = ClickersAndUpgrades.AutoClicker(30, 0, 1)
    val joshTandy = ClickersAndUpgrades.AutoClicker(50, 0, 1)

    private fun gameLoop(view: StoreActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (!gameLoopRunning) { return }

                    ClickersAndUpgrades.addClicker(numTotalRodneyUpgrades, numTotalHeliosUpgrades, numTotalEternalFlameUpgrades, numTotalKoontzUpgrades, numTotalJoshTandyUpgrades, rodney, helios, eternalFlame, koontz, joshTandy)
                    ClickersAndUpgrades.addMultiplier(numTotalRodneyMultipliers, numTotalHeliosMultipliers, numTotalEternalFlameMultipliers, numTotalKoontzMultipliers, numTotalJoshTandyMultipliers, rodney, helios, eternalFlame, koontz, joshTandy)
                    val rodneyAdd = (rodney.dps * rodney.numOwned * rodney.multiplier)
                    val heliosAdd = (helios.dps * helios.numOwned * helios.multiplier)
                    val eternalFlameAdd = (eternalFlame.dps * eternalFlame.numOwned * eternalFlame.multiplier)
                    val koontzAdd = (koontz.dps * koontz.numOwned * koontz.multiplier)
                    val joshTandyAdd = (joshTandy.dps * joshTandy.numOwned * joshTandy.multiplier)

                    val toAdd = rodneyAdd + heliosAdd + eternalFlameAdd + koontzAdd + joshTandyAdd
                    numRavenDollars += toAdd
                    numTotalRavenDollars += toAdd
                    findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars)
                    findViewById<TextView>(R.id.rodneyValue).text = FormatNum.formatNumberRDPS((numTotalRodneyMultipliers * rodney.dps).toLong())
                    findViewById<TextView>(R.id.heliosValue).text = FormatNum.formatNumberRDPS((numTotalHeliosMultipliers * helios.dps).toLong())
                    findViewById<TextView>(R.id.flameValue).text = FormatNum.formatNumberRDPS((numTotalEternalFlameMultipliers * eternalFlame.dps).toLong())
                    findViewById<TextView>(R.id.koontzValue).text = FormatNum.formatNumberRDPS(((numTotalKoontzMultipliers + 1) * koontz.dps).toLong())
                    findViewById<TextView>(R.id.tandyValue).text = FormatNum.formatNumberRDPS(((numTotalJoshTandyMultipliers + 1) * joshTandy.dps).toLong())
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
            findViewById<TextView>(R.id.currRavenDollars).text =
                FormatNum.formatNumber(numRavenDollars)
            findViewById<TextView>(R.id.currRavenDollars).text = FormatNum.formatNumber(numRavenDollars)
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
    fun buyEternalFlame(view: View) {
        numTotalEternalFlameUpgrades += calcCost(numTotalEternalFlameUpgrades, 500, 1.15)
        eternalFlameCost = setCost(numTotalEternalFlameUpgrades, 500, 1.15)
        findViewById<TextView>(R.id.eternalFlame_cost_text).text = FormatNum.formatNumber(eternalFlameCost.toLong())
        if (numTotalEternalFlameUpgrades >= eternalFlameMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_eternalFlame).visibility = View.VISIBLE
        }
    }
    fun buyKoontz(view: View) {
        numTotalKoontzUpgrades += calcCost(numTotalKoontzUpgrades, 1000, 1.15)
        koontzCost = setCost(numTotalKoontzUpgrades, 1000, 1.15)
        findViewById<TextView>(R.id.koontz_cost_text).text = FormatNum.formatNumber(koontzCost.toLong())
        if (numTotalKoontzUpgrades >= koontzMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_koontz).visibility = View.VISIBLE
        }
    }
    fun buyJoshTandy(view: View) {
        numTotalJoshTandyUpgrades += calcCost(numTotalJoshTandyUpgrades, 2000, 1.15)
        joshTandyCost = setCost(numTotalJoshTandyUpgrades, 2000, 1.15)
        findViewById<TextView>(R.id.joshTandy_cost_text).text = FormatNum.formatNumber(joshTandyCost.toLong())
        if (numTotalJoshTandyUpgrades >= joshTandyMilestone) {
            findViewById<ImageButton>(R.id.buy_multiplier_joshTandy).visibility = View.VISIBLE
        }
    }
    fun buyRodneyMultiplier(view: View) {
        numTotalRodneyMultipliers += calcCost(numTotalRodneyMultipliers - 1, 100, 1.5)

        rodneyMilestone *= 2
        findViewById<ImageButton>(R.id.buy_multiplier_rodney).visibility = View.GONE
    }
    fun buyHeliosMultiplier(view: View) {
        numTotalHeliosMultipliers += calcCost(numTotalHeliosMultipliers - 1, 500, 1.5)
        heliosMilestone *= 2
        findViewById<ImageButton>(R.id.buy_multiplier_helios).visibility = View.GONE
    }
    fun buyEternalFlameMultiplier(view: View) {
        numTotalEternalFlameMultipliers += calcCost(numTotalEternalFlameMultipliers - 1, 750, 1.5)
        eternalFlameMilestone *= 2
        findViewById<ImageButton>(R.id.buy_multiplier_eternalFlame).visibility = View.GONE
    }
    fun buyKoontzMultiplier(view: View) {
        numTotalKoontzMultipliers += calcCost(numTotalKoontzMultipliers - 1, 1250, 1.5)
        koontzMilestone *= 2
        findViewById<ImageButton>(R.id.buy_multiplier_koontz).visibility = View.GONE
    }
    fun buyJoshTandyMultiplier(view: View) {
        numTotalJoshTandyMultipliers += calcCost(numTotalJoshTandyMultipliers - 1, 2250, 1.5)
        joshTandyMilestone *= 2
        findViewById<ImageButton>(R.id.buy_multiplier_joshTandy).visibility = View.GONE
    }

    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        rodneyCost = sharedPref.getString("rodney_cost", "10")!!.toInt()
        heliosCost = sharedPref.getString("helios_cost", "100")!!.toInt()
        eternalFlameCost = sharedPref.getString("eternal_flame_cost", "500")!!.toInt()
        koontzCost = sharedPref.getString("koontz_cost", "1000")!!.toInt()
        joshTandyCost = sharedPref.getString("josh_tandy_cost", "2000")!!.toInt()
        rodneyMilestone = sharedPref.getString("rodney_milestone", "25")!!.toInt()
        heliosMilestone = sharedPref.getString("helios_milestone", "25")!!.toInt()
        eternalFlameMilestone = sharedPref.getString("eternal_flame_milestone", "25")!!.toInt()
        koontzMilestone = sharedPref.getString("koontz_milestone", "25")!!.toInt()
        joshTandyMilestone = sharedPref.getString("josh_tandy_milestone", "25")!!.toInt()
    }
}

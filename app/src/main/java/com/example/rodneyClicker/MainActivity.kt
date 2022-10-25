package com.example.rodneyClicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }

    /**Saves all the information whenever the main activity is paused*/
    override fun onPause() {
        super.onPause()
        saveData()
    }

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

    /** Save Data */
    private fun saveData() {
        val currNum = findViewById<EditText>(R.id.ravenDollars).text.toString()

        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("Raven_Dollars", currNum)
        }.apply()
    }

    /** Load Data */
    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedNum = sharedPref.getString("Raven_Dollars", null)

        if (savedNum != null) {
            findViewById<EditText>(R.id.ravenDollars).setText(savedNum)
        }
    }

    private fun ravenDollarsPerSecond() {
        val viewText = findViewById<TextView>(R.id.ravenDollarsPerSecond)
        val currRDPS = viewText.text.toString().toInt()
        viewText.text = ("Raven Dollars Per Second: " + (currRDPS).toString())
    }

    /**Game loop named render**/
    fun render() {
        ravenDollarsPerSecond()
    }

//    window.requestAnimationFrame(render); //TODO figure out how to call this function
}

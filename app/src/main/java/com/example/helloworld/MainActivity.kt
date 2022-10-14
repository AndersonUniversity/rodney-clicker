package com.example.helloworld

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class AutoClicker(var dps: Int, var cost: Double, var numOwned: Int) {
        fun buy() {
            numOwned += 1
            cost *= 1.5
        }
    }

    private val rodney = AutoClicker(1, 10.0, 0)

    //    /** Called when the user taps the Send button */ //TODO change this to shop page
    //    fun sendMessage(view: View) {
    //        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
    //        val message = editText.text.toString()
    //        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
    //            putExtra(EXTRA_MESSAGE, message)
    //        }
    //        startActivity(intent)
    //    }

    /** Called when the user taps the rodney button */
    fun increaseNum(view: View) {
        val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
        val currNum = ravenDollars.text.toString().toInt()
        ravenDollars.setText((currNum + 1).toString())
    }

    fun addClicker(view: View) {
        val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
        val currNum = ravenDollars.text.toString().toInt()
        if (currNum >= rodney.cost) {
            ravenDollars.setText((currNum - rodney.cost).toString())
            rodney.buy()
        }
        addFunds(this)
    }

    //run every 60th of a second
    fun addFunds(view: MainActivity) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                val ravenDollars = findViewById<EditText>(R.id.ravenDollars)
                val currNum = ravenDollars.text.toString().toInt()
                ravenDollars.setText((currNum + rodney.dps).toString())
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }
}




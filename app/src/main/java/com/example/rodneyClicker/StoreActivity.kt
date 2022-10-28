package com.example.rodneyClicker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    var numClickerUpgrades = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        /**Intent(this, MainActivity::class.java)
         finish()*/
        val i = Intent(this@StoreActivity, MainActivity::class.java)
        i.putExtra("key", numClickerUpgrades)
        startActivity(i)
    }

    fun buyRodney(view: View) {
        numClickerUpgrades += 1
    }
}

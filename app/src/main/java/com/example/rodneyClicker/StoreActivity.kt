package com.example.rodneyClicker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }

    /** Called when the user taps the Home button */
    fun openHomePage(view: View) {
        Intent(this, MainActivity::class.java)
        finish()
    }
}

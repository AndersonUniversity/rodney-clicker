package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upgrade_layout)
    }
    /** Called when the user taps the Shop button */ // TODO change this to shop page
    fun openHomePage(view: View) {
        val buttonClick = findViewById<Button>(R.id.home_button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
        }
    }
}

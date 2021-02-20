package com.ntsan.breakingbad

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        window.setBackgroundDrawable(null)
        supportActionBar?.hide()

        val goToRegActivity = findViewById<TextView>(R.id.registrationTextView)
        goToRegActivity.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
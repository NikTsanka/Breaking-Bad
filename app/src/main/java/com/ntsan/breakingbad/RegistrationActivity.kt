package com.ntsan.breakingbad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)
        window.setBackgroundDrawable(null)
        supportActionBar?.hide()

        val goToLoginActivity = findViewById<TextView>(R.id.backButtonTextView )
        goToLoginActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
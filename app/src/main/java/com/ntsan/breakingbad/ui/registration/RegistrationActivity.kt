package com.ntsan.breakingbad.ui.registration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_DATA
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_USERNAME

class RegistrationActivity : AppCompatActivity() {

    private var backButton: LinearLayout? = null
    private var registerBtn: Button? = null
    private var userNameInput: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)
        setContentView(R.layout.activity_registration)
        backButton = findViewById(R.id.backBtnRegScr)
        backButton?.setOnClickListener { onBackPressed() }
        registerBtn = findViewById(R.id.registrationBtnRegScr)
        registerBtn?.setOnClickListener { returnUsername() }
        userNameInput = findViewById(R.id.username2InputEditText)
        userNameInput?.setText(intent.getStringExtra(KEY_DATA))
    }

    private fun returnUsername() {
        val username = userNameInput?.text.toString()
        val intent = Intent().putExtra(KEY_USERNAME, username)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    class RegistrationActivityContract : ActivityResultContract<String, String?>() {

        override fun createIntent(context: Context, input: String?) =
            Intent(context, RegistrationActivity::class.java).apply {
                putExtra(KEY_DATA, input)
            }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getStringExtra(KEY_USERNAME)
            }
            return null
        }

        companion object {
            const val KEY_USERNAME = "key_username"
            const val KEY_DATA = "key_data"
        }
    }
}
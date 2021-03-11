package com.ntsan.breakingbad.ui.registration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.databinding.ActivityRegistrationBinding
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_DATA
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_USERNAME

class RegistrationActivity : LanguageAwareActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnRegScr.setOnClickListener { onBackPressed() }
        binding.registrationBtnRegScr.setOnClickListener { returnUsername() }
        binding.usernameInputRegScrET.setText(intent.getStringExtra(KEY_DATA))
    }

    private fun returnUsername() {
        val username = binding.usernameInputRegScrET.text.toString()
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
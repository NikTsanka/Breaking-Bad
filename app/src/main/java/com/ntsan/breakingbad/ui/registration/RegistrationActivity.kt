package com.ntsan.breakingbad.ui.registration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.lifecycleScope
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.data.models.UserRegistration
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.ActivityRegistrationBinding
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_DATA
import com.ntsan.breakingbad.ui.registration.RegistrationActivity.RegistrationActivityContract.Companion.KEY_USERNAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RegistrationActivity : LanguageAwareActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtnTv.setOnClickListener { onBackPressed() }
        binding.registrationButton.setOnClickListener {
            registerUser()
        }

        binding.userNameInputEt.setText(intent.getStringExtra(KEY_DATA))
    }

    private fun registerUser() = lifecycleScope.launchWhenCreated {
        val name = binding.nameInputEt.text
        val userName = binding.userNameInputEt.text
        val password = binding.passwordInputEt.text
        val confirmPassword = binding.passwordConfirmInputEt.text

        if (name.isNullOrBlank()
            || userName.isNullOrBlank()
            || password.isNullOrBlank()
            || confirmPassword.isNullOrBlank()
        ) {
                Toast.makeText(
                    this@RegistrationActivity,
                    getString(R.string.enter_username_or_password),
                    Toast.LENGTH_LONG
                )
                    .show()
                return@launchWhenCreated
        }
        showLoading()
        try {
            val response = withContext(Dispatchers.IO) {
                val user = UserRegistration(
                    userName = userName.toString(),
                    password = password.toString(),
                    name = name.toString()
                )
                NetworkClient.userService.createUser(content_type = "application/json", user)
            }
            if (response.code() == 200) {
                Toast.makeText(
                    this@RegistrationActivity,
                    getString(R.string.successfully_registration),
                    Toast.LENGTH_LONG
                ).show()
                returnUsername()
            } else {
                showDialog(R.string.common_error, response.message())
            }
        } catch (e: IOException) {
            showDialog(
                R.string.common_error,
                e.message ?: getString(R.string.common_unknown_error)
            )
        } finally {
            hideLoading()
        }
    }

    private fun returnUsername() {
        val username = binding.userNameInputEt.text.toString()
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
package com.ntsan.breakingbad.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.ActivityLoginBinding
import com.ntsan.breakingbad.ui.profile.ProfileActivity
import com.ntsan.breakingbad.ui.registration.RegistrationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private val registrationActivityContract =
        registerForActivityResult(RegistrationActivity.RegistrationActivityContract()) {
            binding.userNameInput.setText(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationTvLogScr.setOnClickListener(this)
        binding.loginButtonLogScr.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.registrationTvLogScr -> {
                startRegistrationActivity()
            }
            binding.loginButtonLogScr -> {
                login()
            }
        }
    }

    private fun login() = lifecycleScope.launchWhenCreated {
        val username = binding.userNameInput.text
        val password = binding.passwordInput.text
        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            Toast.makeText(
                this@LoginActivity,
                getString(R.string.enter_username_or_password),
                Toast.LENGTH_LONG
            )
                .show()
            return@launchWhenCreated
        }
        showLoading()
        try {
            val response = withContext(Dispatchers.IO) {
                NetworkClient.userService.login(
                    username = username.toString(),
                    password = password.toString()
                )
            }
            if (response.isSuccessful) {
                DataStore.authToken = response.body()?.accessToken!!
                startProfileActivity()
            } else {
                showDialog(R.string.common_error, response.message())
            }
            hideLoading()
        } catch (e: IOException) {
            showDialog(
                R.string.common_error,
                e.message ?: getString(R.string.common_unknown_error)
            )
        } finally {
            hideLoading()
        }
    }

    private fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun startRegistrationActivity() {
        registrationActivityContract.launch(binding.userNameInput.text.toString())
    }
}
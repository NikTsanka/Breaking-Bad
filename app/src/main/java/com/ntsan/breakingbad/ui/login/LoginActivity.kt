package com.ntsan.breakingbad.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.databinding.ActivityLoginBinding
import com.ntsan.breakingbad.ui.profile.ProfileActivity
import com.ntsan.breakingbad.ui.registration.RegistrationActivity

class LoginActivity : LanguageAwareActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding


    private val registrationActivityContract =
        registerForActivityResult(RegistrationActivity.RegistrationActivityContract()) {
            binding.usernameInputEtLogScr.setText(it)
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
                startProfileActivity()
            }
        }
    }

    private fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun startRegistrationActivity() {
        registrationActivityContract.launch(binding.usernameInputEtLogScr.text.toString())
    }


}
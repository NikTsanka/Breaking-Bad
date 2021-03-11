package com.ntsan.breakingbad.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.ActivityProfileBinding
import com.ntsan.breakingbad.ui.dialogs.LanguagePickerBottomSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ProfileActivity : LanguageAwareActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.languageBtn.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(supportFragmentManager, "tag")
        }

        binding.logoutBtn.setOnClickListener {
            DataStore.authToken = null
            onBackPressed()
        }
        binding.emailTv.setOnClickListener { sendEmailSupport() }

        getUserData()
    }

    private fun getUserData() {
        lifecycleScope.launchWhenCreated {
            showLoading()
            try {
                val response = withContext(Dispatchers.IO) {
                    NetworkClient.userService.getUser()
                }
                if (!response.isSuccessful) {
                    showDialog(R.string.common_error, response.message())
                } else {
                    binding.userNameTv.text = response.body()?.userName
                    binding.nameTv.text = response.body()?.name
                    Glide.with(this@ProfileActivity)
                        .load(response.body()?.imageUrl)
                        .centerCrop()
                        .into(binding.profilePictureIv)
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
    }

    private fun sendEmailSupport() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_EMAIL, "nik.tsanka@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
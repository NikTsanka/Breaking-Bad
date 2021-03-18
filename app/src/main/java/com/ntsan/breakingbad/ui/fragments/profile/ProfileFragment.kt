package com.ntsan.breakingbad.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.hideLoading
import com.ntsan.breakingbad.base.showDialog
import com.ntsan.breakingbad.base.showLoading
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.FragmentProfileBinding
import com.ntsan.breakingbad.ui.dialogs.LanguagePickerBottomSheet
import com.ntsan.breakingbad.ui.fragments.home.HomeFragment
import com.ntsan.breakingbad.ui.fragments.login.LoginFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.languageBtn.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(childFragmentManager, "tag")
        }

        binding.logoutBtn.setOnClickListener {
            startLoginFragment()
            DataStore.authToken = null
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
                binding.userNameTv.text = response.userName
                binding.nameTv.text = response.name
                Glide.with(this@ProfileFragment)
                    .load(response.imageUrl)
                    .centerCrop()
                    .into(binding.profilePictureIv)
            } catch (e: Exception) {
                when {
                    e is IOException -> {
                        showDialog(R.string.common_error, "No internet connection")
                    }
                    e is HttpException && e.code() == 403 -> {
                        activity?.findNavController(R.id.mainContainer)?.navigate(R.id.loginFragment)
                    }
                    else -> showDialog(
                        R.string.common_error,
                        e.message ?: getString(R.string.common_unknown_error)
                    )
                }
            } finally {
                hideLoading()
            }
        }
    }

    private fun startLoginFragment(){
        activity?.findNavController(R.id.mainContainer)?.navigate(R.id.loginFragment)
    }

    private fun sendEmailSupport() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_EMAIL, "nik.tsanka@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        if (context?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }
}
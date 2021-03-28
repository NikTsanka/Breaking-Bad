package com.ntsan.breakingbad.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.data.models.user.UserProfile
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.FragmentProfileBinding
import com.ntsan.breakingbad.ui.dialogs.LanguagePickerBottomSheet
import com.ntsan.breakingbad.ui.fragments.login.LoginViewModel
import com.ntsan.breakingbad.utils.observeEvent


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<ProfileViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.languageBtn?.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(childFragmentManager, "tag")
        }
        binding?.logoutBtn?.setOnClickListener {
            DataStore.authToken = null
            findNavController().navigate(R.id.show_home)
        }
        viewModel.userProfile.observe(viewLifecycleOwner, this::showUserData)
        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.loginFragment)
        }
        loginViewModel.loginFlowFinished.observeEvent(viewLifecycleOwner) { loginSuccess ->
            if (loginSuccess)
                viewModel.getUserData()
            else
                findNavController().navigate(R.id.show_home)
        }
        binding?.emailTv?.setOnClickListener { sendEmailSupport() }
    }

    private fun showUserData(userProfile: UserProfile) {
        binding?.userNameTv?.text = userProfile.userName
        binding?.nameTv?.text = userProfile.name
        Glide.with(this@ProfileFragment)
            .load(userProfile.imageUrl)
            .centerCrop()
            .into(binding?.profilePictureIv!!)
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
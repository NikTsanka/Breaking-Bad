package com.ntsan.breakingbad.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.hideLoading
import com.ntsan.breakingbad.base.showDialog
import com.ntsan.breakingbad.base.showLoading
import com.ntsan.breakingbad.data.models.user.UserRegistration
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backBtnTv?.setOnClickListener { activity?.onBackPressed() }
        binding?.registrationButton?.setOnClickListener { registerUser() }
    }

    private fun registerUser() = lifecycleScope.launchWhenCreated {
        val name = binding?.nameInputEt?.text
        val userName = binding?.userNameInputEt?.text
        val password = binding?.passwordInputEt?.text
        val confirmPassword = binding?.passwordConfirmInputEt?.text

        if (name.isNullOrBlank()
            || userName.isNullOrBlank()
            || password.isNullOrBlank()
            || confirmPassword.isNullOrBlank()
        ) {
            if (password != confirmPassword) {
                Toast.makeText(
                    context,
                    "Not the same password",
                    Toast.LENGTH_LONG
                )
                    .show()
                return@launchWhenCreated
            }
            Toast.makeText(
                context,
                getString(R.string.enter_username_or_password),
                Toast.LENGTH_LONG
            )
                .show()
            return@launchWhenCreated
        } else {
            showLoading()
            try {
                withContext(Dispatchers.IO) {
                    val user = UserRegistration(
                        userName = userName.toString(),
                        password = password.toString(),
                        name = name.toString()
                    )
                    NetworkClient.userService.createUser(content_type = "application/json", user)
                }
                returnUsername()
            } catch (e: Exception) {
                showDialog(
                    R.string.common_error,
                    e.message ?: getString(R.string.common_unknown_error)
                )
            } finally {
                hideLoading()
            }
        }
    }

    private fun returnUsername() {
        val username = binding?.userNameInputEt?.text.toString()
        setFragmentResult(KEY_DATA, bundleOf(KEY_USERNAME to username))
        parentFragmentManager.popBackStack()
    }

    companion object {
        const val KEY_USERNAME = "key_username"
        const val KEY_DATA = "key_data"
    }
}
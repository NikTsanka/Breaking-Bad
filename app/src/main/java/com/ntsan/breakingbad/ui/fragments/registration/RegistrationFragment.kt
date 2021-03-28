package com.ntsan.breakingbad.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.*
import com.ntsan.breakingbad.data.models.user.UserRegistration
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationFragment : BaseFragment() {

    private var binding: FragmentRegistrationBinding? = null
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backBtnTv?.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        binding?.registrationButton?.setOnClickListener {
            viewModel.onRegister(
                name = binding?.nameInputEt?.text,
                username = binding?.userNameInputEt?.text,
                password = binding?.passwordInputEt?.text,
                confirmPassword = binding?.passwordConfirmInputEt?.text
            )
        }
        viewModel.validationError.observe(viewLifecycleOwner,this::showValidationError)
        viewModel.registrationComplete.observe(viewLifecycleOwner){
            findNavController().popBackStack(R.id.loginFragment,true)
        }
    }

    private fun showValidationError(error: RegistrationViewModel.ValidationError) {
        binding?.apply {
            when (error) {
                RegistrationViewModel.ValidationError.EmptyUsername -> {
                    userNameInputEt.error = getString(R.string.registration_error_empty_username)
                }
                RegistrationViewModel.ValidationError.EmptyName -> {
                    nameInputEt.error = getString(R.string.registration_error_empty_name)
                }
                RegistrationViewModel.ValidationError.EmptyPassword -> {
                    passwordInputEt.error = getString(R.string.registration_error_empty_password)
                }
                RegistrationViewModel.ValidationError.PasswordsNotMatching -> {
                    passwordConfirmInputEt.error =
                        getString(R.string.registration_error_passwords_not_matching)
                }
                RegistrationViewModel.ValidationError.None -> {
                    userNameInputEt.error = null
                    nameInputEt.error = null
                    passwordInputEt.error = null
                    passwordConfirmInputEt.error = null
                }
            }
        }
    }

    companion object {
        const val KEY_USERNAME = "key_username"
        const val KEY_DATA = "key_data"
    }
}
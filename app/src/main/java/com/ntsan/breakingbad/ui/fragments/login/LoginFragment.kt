package com.ntsan.breakingbad.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.*
import com.ntsan.breakingbad.databinding.FragmentLoginBinding
import com.ntsan.breakingbad.ui.fragments.registration.RegistrationFragment

class LoginFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentLoginBinding? = null

    private val viewModel: LoginViewModel by activityViewModels()

    override fun getViewModelInstance() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setFragmentResult(KEY_LOGIN_RESULT, bundleOf(KEY_LOGIN_RESULT_SUCCESS to false))
        setFragmentResultListener(RegistrationFragment.KEY_DATA) { _, bundle ->
            binding?.userNameInput?.setText(bundle.getString(RegistrationFragment.KEY_USERNAME))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.registrationTvLogScr?.setOnClickListener(this)
        binding?.loginButtonLogScr?.setOnClickListener(this)
        viewModel.inputError.observe(viewLifecycleOwner) {
            binding?.passwordInput?.error = getString(it)
        }
        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.loginFragmentStarted()
    }

    override fun onClick(v: View?) {
        hideKeyboard()
        when (v) {
            binding?.registrationTvLogScr -> {
                startRegistration()
            }
            binding?.loginButtonLogScr -> {
                viewModel.login(
                    username = binding?.userNameInput?.text,
                    password = binding?.passwordInput?.text
                )
            }
        }
    }

    override fun onDestroy() {
        viewModel.loginFragmentDestroyed()
        super.onDestroy()
    }

    private fun startRegistration() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    companion object {
        const val KEY_LOGIN_RESULT = "key_login_result"
        const val KEY_LOGIN_RESULT_SUCCESS = "key_login_result_success"
    }
}
package com.ntsan.breakingbad.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.hideLoading
import com.ntsan.breakingbad.base.showDialog
import com.ntsan.breakingbad.base.showLoading
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.FragmentLoginBinding
import com.ntsan.breakingbad.ui.fragments.home.HomeFragment
import com.ntsan.breakingbad.ui.fragments.registration.RegistrationFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResult(KEY_LOGIN_RESULT, bundleOf(KEY_LOGIN_RESULT_SUCCESS to false))
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

    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.registrationTvLogScr -> {
                startRegistration()
            }
            binding?.loginButtonLogScr -> {
                login()
            }
        }
    }

    private fun login() = lifecycleScope.launchWhenCreated {
        val username = binding?.userNameInput?.text
        val password = binding?.passwordInput?.text
        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            Toast.makeText(
                context,
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
            DataStore.authToken = response.accessToken
            startHomeFragment()
            hideLoading()
        } catch (e: Exception) {
            showDialog(
                R.string.common_error,
                e.message ?: getString(R.string.common_unknown_error)
            )
        } finally {
            hideLoading()
        }
    }

    private fun startHomeFragment() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.mainContainer, HomeFragment())
            addToBackStack("Home")
        }
        // findNavController().navigate(R.id.homeFragment)
    }

    private fun startRegistration() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    companion object {
        const val KEY_LOGIN_RESULT = "key_login_result"
        const val KEY_LOGIN_RESULT_SUCCESS = "key_login_result_success"
    }
}
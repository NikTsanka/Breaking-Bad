package com.ntsan.breakingbad.ui.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.data.models.user.UserRegistration
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.utils.Event
import com.ntsan.breakingbad.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegistrationViewModel : BaseViewModel() {

    private val _registrationComplete = MutableLiveData<Event<Unit>>()
    val registrationComplete: LiveData<Event<Unit>> get() = _registrationComplete

    private val _validationError = MutableLiveData<ValidationError>()
    val validationError: LiveData<ValidationError> get() = _validationError

    fun onRegister(
        username: CharSequence?,
        name: CharSequence?,
        password: CharSequence?,
        confirmPassword: CharSequence?
    ) = viewModelScope.launch {
        val error = when {
            username.isNullOrEmpty() -> ValidationError.EmptyUsername
            name.isNullOrEmpty() -> ValidationError.EmptyName
            password.isNullOrEmpty() -> ValidationError.EmptyPassword
            password.toString() != confirmPassword.toString() -> ValidationError.PasswordsNotMatching
            else -> ValidationError.None
        }
        _validationError.postValue(error)
        if (error != ValidationError.None) return@launch
        showLoading()
        try {
            withContext(Dispatchers.IO) {
                val user = UserRegistration(
                    name = name.toString(),
                    userName = username.toString(),
                    password = password.toString()
                )
                NetworkClient.userService.createUser(content_type = "application/json", user)

                val userToken = NetworkClient.userService.login(
                    username = username.toString(),
                    password = password.toString()
                )
                DataStore.authToken = userToken.accessToken
            }
            _registrationComplete.postValue(Event(Unit))
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    enum class ValidationError {
        EmptyUsername, EmptyName, EmptyPassword, PasswordsNotMatching, None
    }

}
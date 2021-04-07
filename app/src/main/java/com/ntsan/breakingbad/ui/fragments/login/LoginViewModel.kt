package com.ntsan.breakingbad.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.utils.Event
import com.ntsan.breakingbad.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {

    private val _inputError = MutableLiveData<Int>()
    val inputError: LiveData<Int> get() = _inputError

    private val _loginSuccess = MutableLiveData<Event<Unit>>()
    val loginSuccess: LiveData<Event<Unit>> get() = _loginSuccess

    private val _loginFlowFinished = MutableLiveData<Event<Boolean>>()
    val loginFlowFinished: LiveData<Event<Boolean>> get() = _loginFlowFinished

    fun login(username: CharSequence?, password: CharSequence?) = viewModelScope.launch {
        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            _inputError.postValue(R.string.enter_username_or_password)
            return@launch
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
            _loginSuccess.postValue(Event(Unit))
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    internal fun loginFragmentDestroyed(){
        _loginFlowFinished.postValue(Event(!DataStore.authToken.isNullOrBlank()))
    }

    internal fun loginFragmentStarted() {
        DataStore.authToken = null
    }

    override fun onUnauthorized() {
        _inputError.postValue(R.string.login_screen_wrong_credentials)
    }
}
package com.ntsan.breakingbad.ui.savedcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.utils.Event
import com.ntsan.breakingbad.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedCardsViewModel : BaseViewModel() {

    private val _requestLogin = MutableLiveData<Event<Unit>>()
    val requestLogin: LiveData<Event<Unit>> get() = _requestLogin

    private val _userCards = MutableLiveData<List<BreakingBadCharacters>>()
    val userCards: LiveData<List<BreakingBadCharacters>> get() = _userCards

    init {
        getSavedCards()
    }

    fun getSavedCards() = viewModelScope.launch {
        try {
            showLoading()
            val cardIds = withContext(Dispatchers.IO) { NetworkClient.userService.getUserCards() }
            val cards = withContext(Dispatchers.IO) {
                cardIds.map {
                    NetworkClient.breakingBadService.getCardById(it).data // TODO: არ იღებს Id -ს
                }
            }
            _userCards.postValue(cards)
            // TODO:  არ შემოდის დებაგერი
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    override fun onUnauthorized() {
        _requestLogin.postValue(Event(Unit))
    }
}
package com.ntsan.breakingbad.ui.savedcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.data.repository.Repository
import com.ntsan.breakingbad.utils.Event
import com.ntsan.breakingbad.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
            val card = withContext(Dispatchers.IO) {
                cardIds.mapNotNull {
                    NetworkClient.breakingBadService.getCardById(it).firstOrNull()
                }
            }
            _userCards.postValue(card)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }


    /*val userCards: LiveData<List<BreakingBadCharacters>> =
        Repository.getLocalSavedCardsFlow().map { list ->
            list.map { id ->
                var card: BreakingBadCharacters? = null
                card = Repository.getRemoteCardById(id)
                if (card == null) {
                    showLoading()
                    card = Repository.getRemoteCardById(id)
                    hideLoading()
                }
                card
            }
        }.catch { error -> handleNetworkError(error) }
            .flowOn(Dispatchers.IO)
            .asLiveData(viewModelScope.coroutineContext)

    init {
        getSavedCards()
    }

    fun getSavedCards() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (!Repository.checkSavedIdsValidity()) {
                showLoading()
                Repository.updateRemoteSavedCards()
            }
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            Repository.updateRemoteSavedCards()
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
        getSavedCards()
    }*/

    override fun onUnauthorized() {
        _requestLogin.postValue(Event(Unit))
    }
}
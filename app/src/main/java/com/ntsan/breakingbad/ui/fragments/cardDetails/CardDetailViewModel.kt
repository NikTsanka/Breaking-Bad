package com.ntsan.breakingbad.ui.fragments.cardDetails

import androidx.lifecycle.*
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.base.DialogData
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.utils.Event
import com.ntsan.breakingbad.utils.handleNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardDetailViewModel(
    private val data: BreakingBadCharacters
) : BaseViewModel() {

    private val _seasonModel = MutableLiveData<List<Int>>()
    val seasonModel: LiveData<List<Int>> get() = _seasonModel

    private val _quoteModel = MutableLiveData<List<BreakingBadQuotes>>()
    val quoteModel: LiveData<List<BreakingBadQuotes>> get() = _quoteModel

    private val _cardModel = MutableLiveData(data)
    val cardModel: LiveData<BreakingBadCharacters> get() = _cardModel

    private val _cardSaved = MutableLiveData<CardSavedState>(CardSavedState.Unknown)
    val cardSaved: LiveData<CardSavedState> get() = _cardSaved

    private val _loginRequired = MutableLiveData<Event<Unit>>()
    val loginRequired: LiveData<Event<Unit>> get() = _loginRequired

    init {
        determineCardSavedState()
        getSeasons()
    }

    private fun getSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _seasonModel.postValue(data.appearance)
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }
    }

     fun getQuotes(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val quotes =
                    NetworkClient.breakingBadService.getQuotesByName(name)
                quotes.map { q -> q.quote }
                _quoteModel.postValue(quotes)
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }
    }

    fun buttonClicked() {
        when (cardSaved.value) {
            CardSavedState.Saved -> deleteCard()
            CardSavedState.NotSaved -> saveCard()
            CardSavedState.Unknown -> _loginRequired.postValue(Event(Unit))
        }
    }

    private fun saveCard() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            NetworkClient.userService.saveUserCards(data.charId)
            _cardSaved.postValue(CardSavedState.Saved)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    private fun deleteCard() = viewModelScope.launch(Dispatchers.IO) {
        try {
            showLoading()
            NetworkClient.userService.deleteUserCard(data.charId)
            _cardSaved.postValue(CardSavedState.NotSaved)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun determineCardSavedState() = viewModelScope.launch {
        try {
            showLoading()
            val cardIds = withContext(Dispatchers.IO) { NetworkClient.userService.getUserCards() }
            val state =
                if (cardIds.contains(data.charId)) CardSavedState.Saved else CardSavedState.NotSaved
            _cardSaved.postValue(state)
        } catch (e: Exception) {
            handleNetworkError(e)
        } finally {
            hideLoading()
        }
    }

    fun trimFirstWord(s: String): String {
        return if (s.contains(" "))
            s.substring(s.indexOf(' '))
                .trim { it <= ' ' } else ""
    }

    fun getFirstWord(s: String, separator: String): String {
        val index = s.indexOf(separator)
        return if (index < 0) s else s.substring(0, index)
    }

    override fun onUnauthorized() {
        _loginRequired.postValue(Event(Unit))
    }

    enum class CardSavedState {
        Unknown, Saved, NotSaved
    }

    @Suppress("UNCHECKED_CAST")
    class CardDetailViewModelFactory(
        private val data: BreakingBadCharacters
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CardDetailViewModel(data) as T
        }
    }
}

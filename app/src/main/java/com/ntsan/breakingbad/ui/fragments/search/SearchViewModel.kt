package com.ntsan.breakingbad.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<BreakingBadCharacters>>()
    val cards: LiveData<List<BreakingBadCharacters>> get() = _cards

    fun onSearchTextChange(string: CharSequence) {
        if (string.length < 3) return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cards = NetworkClient.breakingBadService.getCharacter(
                    name = "$string",
                    limit = 62,
                    offset = 0
                )
                _cards.postValue(cards)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
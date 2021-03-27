package com.ntsan.breakingbad.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondSearchViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<BreakingBadCharacters>>()
    val cards: LiveData<List<BreakingBadCharacters>> get() = _cards

    fun onSearch(name: CharSequence?) {
        if (name.isNullOrEmpty()) _cards.postValue(emptyList())
        if (name?.length ?: 0 < 3) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cards = NetworkClient.findByNameService.findByName("$name")
                _cards.postValue(cards)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
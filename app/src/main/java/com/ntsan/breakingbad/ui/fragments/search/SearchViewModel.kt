package com.ntsan.breakingbad.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.base.DialogData
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    private val _cards = MutableLiveData<List<BreakingBadCharacters>>()
    val cards: LiveData<List<BreakingBadCharacters>> get() = _cards

    val message = MutableLiveData<String>()

    fun onSearchTextChange(string: CharSequence?) {
        if (string.isNullOrEmpty()) _cards.postValue(emptyList())
        if (string?.length ?: 0 < 3) return
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                try {
                    val cards = NetworkClient.breakingBadService.findByName(
                        name = "$string"
                    )
                    _cards.postValue(cards)
                    message.postValue("Count ${cards.size}")
                } catch (e: Exception) {
                    showDialog(
                        DialogData(
                            title = R.string.common_error,
                            message = e.message ?: ""
                        )
                    )
                }
            }
        }
    }
}
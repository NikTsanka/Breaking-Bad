package com.ntsan.breakingbad.ui.fragments.home

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
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeViewModel : BaseViewModel() {

    private val _items = MutableLiveData<List<BreakingBadCharacters>>()
    val items: LiveData<List<BreakingBadCharacters>> get() = _items

    private val _loadingMore = MutableLiveData(false)
    val loadingMore: LiveData<Boolean> get() = _loadingMore

    private var offset = 0

    init {
        loadMoreCard()
    }

    fun onRefresh() {
        offset += 10
        _items.postValue(emptyList())
        loadMoreCard()
    }

    private fun loadMoreCard() {
        viewModelScope.launch {
            _loadingMore.value = true
            try {
                val data = withContext(Dispatchers.IO) {
                    NetworkClient.breakingBadService.getCharacter(
                            limit = CARD_SIZE,
                            offset = offset
                    )
                }
                _items.postValue((_items.value ?: emptyList()) + data)

            } catch (e: IOException) {
                showDialog(DialogData(title = R.string.common_error, message = e.message ?: ""))
            } finally {
               _loadingMore.value = false
            }
        }
    }



    companion object {
        const val CARD_SIZE = 10
    }
}
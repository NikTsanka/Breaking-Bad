package com.ntsan.breakingbad.ui.fragments.cardDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters

class CardDetailViewModel : ViewModel() {

    private val _cardModel = MutableLiveData<BreakingBadCharacters>()
    val cardModel: LiveData<BreakingBadCharacters> get() = _cardModel

    fun setCardData(data: BreakingBadCharacters){
        _cardModel.postValue(data)
    }
}
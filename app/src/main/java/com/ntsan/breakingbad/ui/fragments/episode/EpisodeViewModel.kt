package com.ntsan.breakingbad.ui.fragments.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntsan.breakingbad.base.BaseViewModel
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadEpisodes

class EpisodeViewModel(
    private val data: BreakingBadEpisodes
) : BaseViewModel() {

    private val _episodeModel = MutableLiveData(data)
    val episodeModel: LiveData<BreakingBadEpisodes> get() = _episodeModel



    fun showEpisodes(ep: BreakingBadEpisodes){
        _episodeModel.postValue(ep)
    }

    @Suppress("UNCHECKED_CAST")
    class EpisodeViewModelFactory(private val data: BreakingBadEpisodes) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EpisodeViewModel(data) as T
        }
    }

}
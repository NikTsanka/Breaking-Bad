package com.ntsan.breakingbad.ui.fragments.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadEpisodes
import com.ntsan.breakingbad.databinding.FragmentEpisodeBinding

class EpisodeFragment : Fragment() {

    private var binding: FragmentEpisodeBinding? = null

    private val episodeArg by navArgs<EpisodeFragmentArgs>()

    private val viewModel by viewModels<EpisodeViewModel> {
        EpisodeViewModel.EpisodeViewModelFactory(episodeArg.data)
    }

    private val episodeAdapter = EpisodeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            val layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = episodeAdapter
            recyclerView.layoutManager = layoutManager
            viewModel.episodeModel.observe(viewLifecycleOwner) {
                showEpisodes(it)
            }
            viewModel.episodeModel.observe(viewLifecycleOwner){
                showEpisodes(it)
            }
        }
    }

    private fun showEpisodes(ep: BreakingBadEpisodes) {
        binding?.apply {
            seasonTV.text = ep.season
        }
    }
}
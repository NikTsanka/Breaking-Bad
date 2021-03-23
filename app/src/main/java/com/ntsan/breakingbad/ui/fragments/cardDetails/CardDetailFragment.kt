package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding

class CardDetailFragment : Fragment() {

    private var binding: CardDetailFragmentBinding? = null

    private val cardDetailArg by lazy { arguments?.getParcelable<BreakingBadCharacters>(KEY_DATA) }

    private val viewModel by viewModels<CardDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCardData(cardDetailArg!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cardModel.observe(viewLifecycleOwner){

        }
    }

    private fun showCardData(card: BreakingBadCharacters) {

    }

    companion object {
        const val KEY_DATA = "key_data"
    }
}
package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.transition.TransitionInflater.from
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.appcompat.widget.LinearLayoutCompat.HORIZONTAL
import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseFragment
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding
import com.ntsan.breakingbad.databinding.DetailSeasonItemBinding
import com.ntsan.breakingbad.ui.fragments.home.CardAdapter
import com.ntsan.breakingbad.ui.fragments.login.LoginViewModel
import com.ntsan.breakingbad.utils.observeEvent

class CardDetailFragment : BaseFragment() {

    private var binding: CardDetailFragmentBinding? = null
    private var seasonDetail: DetailSeasonItemBinding? = null

    private val cardDetailArg by navArgs<CardDetailFragmentArgs>()
    private val viewModel by viewModels<CardDetailViewModel> {
        CardDetailViewModel.CardDetailViewModelFactory(cardDetailArg.data)
    }
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    private val adapter = CardAdapter() {

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
        viewModel.cardModel.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenStarted { showCardData(it) }
        }
        binding?.backBtnTv?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.addRemoveBtn?.setOnClickListener {
            viewModel.buttonClicked()
        }
        viewModel.cardSaved.observe(viewLifecycleOwner) {
            when (it) {
                CardDetailViewModel.CardSavedState.NotSaved -> binding?.addRemoveBtn?.setText(R.string.card_details_add)
                CardDetailViewModel.CardSavedState.Saved -> binding?.addRemoveBtn?.setText(R.string.card_details_remove)
                else -> binding?.addRemoveBtn?.setText(R.string.card_details_login)
            }
        }
        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.loginFragment)
        }
        loginViewModel.loginFlowFinished.observeEvent(viewLifecycleOwner) {
            if (it) viewModel.determineCardSavedState()
        }


        binding?.apply {
            val layoutManager =
                LinearLayoutManager(context) // TODO: 4/6/2021 OrientationHelper.Horizontal  needed
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            getCharacters()
        }
    }

    private fun getCharacters() {
        lifecycleScope.launchWhenStarted {
            try {
                val seasons =
                    NetworkClient.breakingBadService.getCharacter(limit = 1, offset = 0)
                adapter.cardList = seasons
            } catch (e: Exception) {

            }
        }
    }

    private fun showSeasons(season: BreakingBadCharacters) {
        seasonDetail?.apply {
            seasonCountTv.text = season.appearance.toString()
        }
    }

    private suspend fun showCardData(card: BreakingBadCharacters) {
        binding?.apply {
            Glide.with(cardIV)
                .load(card.img)
                .into(cardIV)
            nameTv.text = getFirstWord(card.name, " ")
            lastNameTv.text = trimFirstWord(card.name)
            nicknameTv.text = card.nickname
            occupationTV.text = card.occupation.joinToString()
            birthdayContentTv.text = card.birthday
            statusContentTv.text = card.status
            portrayedContentTv.text = card.portrayed
//            seasonCountTv.text = card.appearance.toString()
//                .substring(1, card.appearance.toString().length - 1)
//                .replace(",", "")

            val data = NetworkClient.breakingBadService.getQuotesByName(
                author = card.name
            )
            data.forEach { showQuotes(it) }
        }
    }

    private fun showQuotes(item: BreakingBadQuotes) {
        binding?.apply {
            quoteTV.text = item.quote
        }
    }

    private fun trimFirstWord(s: String): String {
        return if (s.contains(" "))
            s.substring(s.indexOf(' '))
                .trim { it <= ' ' } else ""
    }

    private fun getFirstWord(s: String, separator: String): String {
        val index = s.indexOf(separator)
        return if (index < 0) s else s.substring(0, index)
    }
}

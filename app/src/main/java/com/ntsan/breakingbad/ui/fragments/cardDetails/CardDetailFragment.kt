package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseFragment
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding
import com.ntsan.breakingbad.ui.fragments.login.LoginViewModel
import com.ntsan.breakingbad.utils.observeEvent

class CardDetailFragment : BaseFragment() {

    private var binding: CardDetailFragmentBinding? = null

    private val cardDetailArg by navArgs<CardDetailFragmentArgs>()

    private val viewModel by viewModels<CardDetailViewModel> {
        CardDetailViewModel.CardDetailViewModelFactory(cardDetailArg.data)
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

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
            showCardData(it)
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
    }

    private fun showCardData(card: BreakingBadCharacters) {
        binding?.apply {
            Glide.with(cardIV)
                .load(card.img)
                .centerCrop()
                .into(cardIV)
            nameTv.text = card.name.split(" ")[0]
            lastNameTv.text = card.name
            nicknameTv.text = card.nickname
            occupationTV.text = card.occupation.toString()
            birthdayContentTv.text = card.birthday
            statusContentTv.text = card.status
            portrayedContentTv.text = card.portrayed
            seasonCountTv.text = card.appearance.toString().split(",")[0]
        }
    }

    companion object {
        const val KEY_DATA = "key_data"
    }
}
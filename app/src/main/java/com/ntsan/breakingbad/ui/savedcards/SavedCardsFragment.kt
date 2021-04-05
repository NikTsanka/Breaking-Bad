package com.ntsan.breakingbad.ui.savedcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseFragment
import com.ntsan.breakingbad.databinding.SavedCardsScreenBinding
import com.ntsan.breakingbad.ui.fragments.cardDetails.CardDetailFragmentDirections
import com.ntsan.breakingbad.ui.fragments.login.LoginViewModel
import com.ntsan.breakingbad.utils.observeEvent

class SavedCardsFragment : BaseFragment() {

    private val viewModel by viewModels<SavedCardsViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    private var binding: SavedCardsScreenBinding? = null

    private var adapter = SavedCardAdapter() {
        val action = CardDetailFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SavedCardsScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)

            viewModel.requestLogin.observeEvent(viewLifecycleOwner) {
                login()
            }
            viewModel.userCards.observe(viewLifecycleOwner) {
                adapter.cardList = it
            }
            loginViewModel.loginFlowFinished.observeEvent(viewLifecycleOwner) { loginSuccess ->
                if (loginSuccess)
                    viewModel.getSavedCards()
                else
                    findNavController().navigate(R.id.show_home)
            }
        }
    }

    private fun login() {
        activity?.findNavController(R.id.mainContainer)?.navigate(R.id.loginFragment)
    }
}
package com.ntsan.breakingbad.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.databinding.FragmentSearchBinding
import com.ntsan.breakingbad.ui.fragments.cardDetails.CardDetailFragmentDirections
import com.ntsan.breakingbad.ui.fragments.home.CardAdapter
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()

    private val adapter = CardAdapter() {
        val action = CardDetailFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = CardAdapter.LoaderSpanSizeLookup(adapter)
        binding?.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                BreakingBadCardDecorator(
                    itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen._16dp),
                    itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen._18dp),
                    itemVerticalInsets = resources.getDimensionPixelSize(R.dimen._0dp),
                    itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen._4dp)
                )
            )
            viewModel.cards.observe(viewLifecycleOwner) {
                adapter.cardList = it
            }
            searchInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchTextChange(text)
            }
            viewModel.message.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
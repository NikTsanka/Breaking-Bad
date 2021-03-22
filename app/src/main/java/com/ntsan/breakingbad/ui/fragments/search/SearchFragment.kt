package com.ntsan.breakingbad.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.BreakingBadItemBinding
import com.ntsan.breakingbad.databinding.FragmentSearchBinding
import com.ntsan.breakingbad.ui.fragments.home.CardAdapter
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var binding: FragmentSearchBinding? = null

    private var characterList = mutableListOf<BreakingBadCharacters>()
    private val adapter = CardAdapter{

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
        binding?.recycleView?.layoutManager = GridLayoutManager(context, 2)
        binding?.recycleView?.adapter = adapter
        binding?.recycleView?.addItemDecoration(
            BreakingBadCardDecorator(
                itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen._16dp),
                itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen._18dp),
                itemVerticalInsets = resources.getDimensionPixelSize(R.dimen._0dp),
                itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen._4dp)
            )
        )
        viewModel.cards.observe(viewLifecycleOwner) {
            characterList.clear()
            characterList.addAll(it)
            adapter.notifyDataSetChanged()
        }
        binding?.searchInput?.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                viewModel.onSearchTextChange(text)
            }
        }
    }
}
package com.ntsan.breakingbad.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ntsan.breakingbad.databinding.SecondSearchScreenBinding
import com.ntsan.breakingbad.ui.fragments.home.CardAdapter
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator

class SecondSearchFragment : Fragment() {

    private val viewModel: SecondSearchViewModel by viewModels()
    private var binding: SecondSearchScreenBinding? = null

    private val adapter = CardAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondSearchScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = LoaderSpanSizeLookup()
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.addItemDecoration(
            BreakingBadCardDecorator(
                itemHorizontalInsets = 10,
                itemHorizontalSpacing = 0,
                itemVerticalInsets = 13,
                itemVerticalSpacing = 60
            )
        )
        viewModel.cards.observe(viewLifecycleOwner){
            adapter.cardList = it
        }
        binding?.searchInput?.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearch(text)
        }
    }

    inner class LoaderSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.itemCount - 1 == position) 2 else 1
        }
    }
}
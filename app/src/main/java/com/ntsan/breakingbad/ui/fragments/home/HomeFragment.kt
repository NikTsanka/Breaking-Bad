package com.ntsan.breakingbad.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseFragment
import com.ntsan.breakingbad.databinding.FragmentHomeBinding
import com.ntsan.breakingbad.ui.fragments.cardDetails.CardDetailFragment
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewModelInstance() = viewModel

    private val adapter = CardAdapter() {
        activity?.findNavController(R.id.mainContainer)
                ?.navigate(R.id.cardDetailFragment, bundleOf(CardDetailFragment.KEY_DATA to it))
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = CardAdapter.LoaderSpanSizeLookup(adapter)
        binding?.apply {
            recycleView.layoutManager = layoutManager
            recycleView.adapter = adapter
            recycleView.addItemDecoration(
                    BreakingBadCardDecorator(
                            itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen._16dp),
                            itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen._18dp),
                            itemVerticalInsets = resources.getDimensionPixelSize(R.dimen._0dp),
                            itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen._4dp)
                    )
            )
            viewModel.items.observe(viewLifecycleOwner) {
                adapter.cardList = it
            }
            swipeToRefresh.setOnRefreshListener {
                viewModel.onRefresh()
            }

            viewModel.loadingMore.observe(viewLifecycleOwner) {
                adapter.loadingMore = it
            }
            viewModel.loadingMore.observe(viewLifecycleOwner) {
                adapter.loadingMore = it
                if (swipeToRefresh.isRefreshing && it) swipeToRefresh.isRefreshing = false
            }
        }
    }
}
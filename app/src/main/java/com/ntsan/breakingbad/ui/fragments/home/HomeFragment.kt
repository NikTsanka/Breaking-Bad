package com.ntsan.breakingbad.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.BaseFragment
import com.ntsan.breakingbad.databinding.FragmentHomeBinding
import com.ntsan.breakingbad.ui.fragments.cardDetails.CardDetailFragmentDirections
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator
import com.ntsan.breakingbad.utils.LoadMoreListener

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewModelInstance() = viewModel

    private val adapter = CardAdapter() {
        val action = CardDetailFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)?.navigate(action)
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
            recyclerView.addOnScrollListener(LoadMoreListener() {
                viewModel.onScrollEndReached()
            })
            swipeToRefresh.setOnRefreshListener {
                viewModel.onRefresh()
            }
            viewModel.items.observe(viewLifecycleOwner) {
                adapter.cardList = it
            }
            viewModel.loadingMore.observe(viewLifecycleOwner) {
                adapter.loadingMore = it
            }
            viewModel.loadingMore.observe(viewLifecycleOwner) {
                if (swipeToRefresh.isRefreshing && it) swipeToRefresh.isRefreshing = false
                adapter.loadingMore = it
            }
            viewModel.message.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}
package com.ntsan.breakingbad.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.showDialog
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.FragmentHomeBinding
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private var offset = 0

    private var characterList = mutableListOf<BreakingBadCharacters>()
    private val adapter = CardAdapter(characterList) {
        activity?.findNavController(R.id.mainContainer)
            ?.navigate(R.id.cardDetailFragment, bundleOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = LoaderSpanSizeLookup()
        binding.recycleView.layoutManager = layoutManager
        binding.recycleView.adapter = adapter
        binding.recycleView.addItemDecoration(
            BreakingBadCardDecorator(
                itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen._16dp),
                itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen._18dp),
                itemVerticalInsets = resources.getDimensionPixelSize(R.dimen._0dp),
                itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen._4dp)
            )
        )
        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = (recyclerView.layoutManager as GridLayoutManager)
                if (recyclerView.adapter?.itemCount == gridLayoutManager.findLastVisibleItemPosition() + 1) {
                    loadMoreCard()
                }
            }
        })
        loadMoreCard()
    }

    private fun loadMoreCard() {
        if (adapter.loadingMore) return
        lifecycleScope.launchWhenCreated {
            adapter.loadingMore = true
            adapter.notifyItemChanged(adapter.itemCount - 1)
            try {
                adapter.loadingMore = true
                val data = withContext(Dispatchers.IO) {
                    NetworkClient.breakingBadService.getCharacter(
                        limit = CARD_SIZE,
                        offset = offset
                    )
                }
                characterList.addAll(data)
                adapter.notifyDataSetChanged()
            } catch (e: IOException) {
                showDialog(R.string.common_error, e.message ?: "")
            } finally {
                adapter.loadingMore = false
                offset += 10
            }
        }
    }

    inner class LoaderSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.itemCount - 1 == position) 2 else 1
        }
    }

    companion object {
        const val CARD_SIZE = 10
        const val VIEW_TYPE_CARD = 1
        const val VIEW_TYPE_LOADER = 2

    }
}
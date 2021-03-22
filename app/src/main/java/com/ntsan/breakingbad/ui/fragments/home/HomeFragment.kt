package com.ntsan.breakingbad.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.hideLoading
import com.ntsan.breakingbad.base.showDialog
import com.ntsan.breakingbad.base.showLoading
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.network.NetworkClient
import com.ntsan.breakingbad.databinding.BreakingBadItemBinding
import com.ntsan.breakingbad.databinding.FragmentHomeBinding
import com.ntsan.breakingbad.utils.BreakingBadCardDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private var characterList = mutableListOf<BreakingBadCharacters>()
    private val adapter = BreakingBadAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.layoutManager = GridLayoutManager(context, 2)
        binding.recycleView.adapter = adapter
        binding.recycleView.addItemDecoration(
            BreakingBadCardDecorator(
                itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen._16dp),
                itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen._18dp),
                itemVerticalInsets = resources.getDimensionPixelSize(R.dimen._0dp),
                itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen._4dp)
            )
        )
        lifecycleScope.launchWhenCreated {
            try {
                showLoading()
                val data = withContext(Dispatchers.IO) {
                    NetworkClient.breakingBadService.getCharacter(
                        limit = CARD_SIZE,
                        offset = CARD_START_ID
                    )
                }
                characterList.addAll(data)
                adapter.notifyDataSetChanged()
            } catch (e: IOException) {
                showDialog(R.string.common_error, e.message ?: "")
            } finally {
                hideLoading()
            }
        }
    }

    inner class BreakingBadAdapter() : RecyclerView.Adapter<BreakingBadViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BreakingBadViewHolder(
                BreakingBadItemBinding.inflate(LayoutInflater.from(parent.context))
            )

        override fun onBindViewHolder(holder: BreakingBadViewHolder, position: Int) {
            val item = characterList[position]
            holder.binding.nameTv.text = item.name
            Glide.with(this@HomeFragment).load(item.img).into(holder.binding.contentIV)
        }

        override fun getItemCount() = characterList.size
    }

    inner class BreakingBadViewHolder(val binding: BreakingBadItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {
        const val CARD_SIZE = 62
        const val CARD_START_ID = 0
    }
}
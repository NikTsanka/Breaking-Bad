package com.ntsan.breakingbad.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.BreakingBadItemBinding
import com.ntsan.breakingbad.databinding.LoadingItemBinding

class CardAdapter(
    private var characterList: List<BreakingBadCharacters>,
    private val onItemClick: (characterCard: BreakingBadCharacters) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var loadingMore = false

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as BreakingBadCharacters
        onItemClick.invoke(card)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) HomeFragment.VIEW_TYPE_LOADER else HomeFragment.VIEW_TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            HomeFragment.VIEW_TYPE_LOADER -> LoadingViewHolder(
                LoadingItemBinding.inflate(LayoutInflater.from(parent.context))
            )
            HomeFragment.VIEW_TYPE_CARD -> CardViewHolder(
                binding = BreakingBadItemBinding.inflate(LayoutInflater.from(parent.context)),
                onClickListener = onClickListener
            )
            else -> throw Exception("unknown ViewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = characterList?.get(position)
                holder.binding.nameTv.text = item.name
                Glide.with(holder.itemView).load(item.img)
                    .into(holder.binding.cardIv)
                holder.binding.root.tag = item
            }
            is LoadingViewHolder -> {
                holder.binding.loader.visibility = if (loadingMore) View.VISIBLE else View.GONE
            }
        }
    }

    override fun getItemCount() = (characterList.size ?: 0) + 1

    inner class LoadingViewHolder(val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class CardViewHolder(
        val binding: BreakingBadItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }
}





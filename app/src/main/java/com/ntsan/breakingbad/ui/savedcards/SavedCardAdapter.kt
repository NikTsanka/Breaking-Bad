package com.ntsan.breakingbad.ui.savedcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.LoadingItemBinding
import com.ntsan.breakingbad.databinding.SavedItemBinding

class SavedCardAdapter(
    private val onItemClick: (characterCard: BreakingBadCharacters) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var loadingMore = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var cardList: List<BreakingBadCharacters> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as BreakingBadCharacters
        onItemClick.invoke(card)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) VIEW_TYPE_LOADER else VIEW_TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            VIEW_TYPE_LOADER -> LoadingViewHolder(
                LoadingItemBinding.inflate(LayoutInflater.from(parent.context))
            )
            VIEW_TYPE_CARD -> SavedCardViewHolder(
                binding = SavedItemBinding.inflate(LayoutInflater.from(parent.context)),
                onClickListener = onClickListener
            )
            else -> throw Exception("unknown ViewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SavedCardViewHolder -> {
                val item = cardList[position]
                Glide.with(holder.itemView).load(item.img)
                    .into(holder.binding.cardIV)
                holder.binding.nameTV.text = item.name
                holder.binding.nicknameTv.text = item.nickname
                holder.binding.portrayed.text = item.portrayed
                holder.binding.root.tag = item
            }
            is LoadingViewHolder -> {
                holder.binding.loader.visibility = if (loadingMore) View.VISIBLE else View.GONE
            }
        }
    }

    override fun getItemCount() = cardList.size + 1

    class LoadingViewHolder(val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SavedCardViewHolder(
        val binding: SavedItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }

    companion object {
        const val VIEW_TYPE_CARD = 1
        const val VIEW_TYPE_LOADER = 2
    }
}
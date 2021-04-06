package com.ntsan.breakingbad.ui.fragments.savedcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.SavedItemBinding

class SavedCardAdapter(
    private val onItemClick: (characterCard: BreakingBadCharacters) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cardList: List<BreakingBadCharacters> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as BreakingBadCharacters
        onItemClick.invoke(card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
         SavedCardViewHolder(
                binding = SavedItemBinding.inflate(LayoutInflater.from(parent.context)),
                onClickListener = onClickListener
            )

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
        }
    }

    override fun getItemCount() = cardList.size

    class SavedCardViewHolder(
        val binding: SavedItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }
}
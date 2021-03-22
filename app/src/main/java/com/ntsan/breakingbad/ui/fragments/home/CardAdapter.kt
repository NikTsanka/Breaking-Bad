package com.ntsan.breakingbad.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.BreakingBadItemBinding

class CardAdapter(
    private val onItemClick: (charactersCard: BreakingBadCharacters) -> Unit
) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var characterList = mutableListOf<BreakingBadCharacters>()

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as BreakingBadCharacters
        onItemClick.invoke(card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(
            binding = BreakingBadItemBinding.inflate(LayoutInflater.from(parent.context)),
            onClickListener = onClickListener
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = characterList.get(position)
        holder.binding.nameTv.text = item.name
        Glide.with(holder.itemView).load(item.img).into(holder.binding.contentIV)
        holder.binding.root.tag = item
    }

    override fun getItemCount() = characterList.size

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





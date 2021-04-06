package com.ntsan.breakingbad.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.databinding.DetailSeasonItemBinding

class AdapterExample(
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
        AppearanceViewHolder(
            binding = DetailSeasonItemBinding.inflate(LayoutInflater.from(parent.context)),
            onClickListener = onClickListener
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppearanceViewHolder -> {
                val item = cardList[position]
                holder.binding.seasonCountTv.text = item.appearance.toString()
                    .substring(1, item.appearance.toString().length - 1)
                    .replace(",", "")
            }
        }
    }

    override fun getItemCount() = cardList.size

    class AppearanceViewHolder(
        val binding: DetailSeasonItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }
}
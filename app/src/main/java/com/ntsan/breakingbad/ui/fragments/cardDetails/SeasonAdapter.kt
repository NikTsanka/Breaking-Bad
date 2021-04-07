package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadEpisodes
import com.ntsan.breakingbad.databinding.BreakingBadItemBinding
import com.ntsan.breakingbad.databinding.DetailSeasonItemBinding
import com.ntsan.breakingbad.databinding.LoadingItemBinding
import com.ntsan.breakingbad.ui.fragments.home.CardAdapter

class SeasonAdapter(
    private val onItemClick: (episodes: BreakingBadEpisodes) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var seasonList: List<Int> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onClickListener = View.OnClickListener { v ->
        println("test")
        val ep = v?.tag as BreakingBadEpisodes
        onItemClick.invoke(ep)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppearanceViewHolder(
            binding = DetailSeasonItemBinding.inflate(LayoutInflater.from(parent.context)),
            onClickListener = onClickListener)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppearanceViewHolder -> {
                val item = seasonList[position]
                holder.binding.seasonCountTv.text = item.toString()
                holder.binding.root.tag = item
            }
        }
    }

    override fun getItemCount() = seasonList.size

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
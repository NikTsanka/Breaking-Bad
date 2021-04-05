package com.ntsan.breakingbad.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding

class AdapterExample : RecyclerView.Adapter<AdapterExample.DetailViewHolder>() {

    private var quoteList: List<BreakingBadQuotes> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailViewHolder(
        CardDetailFragmentBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = quoteList[position]
        holder.binding.quoteTV.text = item.author
    }

    override fun getItemCount() = quoteList.size


    class DetailViewHolder(val binding: CardDetailFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)
}
package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.databinding.DetailQuoteItemBinding

class QuotesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var quotesList: List<BreakingBadQuotes> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppearanceViewHolder(
            binding = DetailQuoteItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppearanceViewHolder -> {
                val item = quotesList[position]
                holder.binding.quoteTV.text = item.quote
            }
        }
    }

    override fun getItemCount() = quotesList.size

    class AppearanceViewHolder(
        val binding: DetailQuoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
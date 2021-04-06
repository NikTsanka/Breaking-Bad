package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.databinding.DetailQuoteItemBinding
import com.ntsan.breakingbad.databinding.DetailSeasonItemBinding

class QuotesAdapter(
    private val onItemClick: (quotes: BreakingBadQuotes) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var quotesList: List<BreakingBadQuotes> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as BreakingBadQuotes
        onItemClick.invoke(card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppearanceViewHolder(
            binding = DetailQuoteItemBinding.inflate(LayoutInflater.from(parent.context)),
            onClickListener = onClickListener
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
        val binding: DetailQuoteItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }
}

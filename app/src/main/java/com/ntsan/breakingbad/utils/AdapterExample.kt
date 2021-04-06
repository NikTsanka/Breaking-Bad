package com.ntsan.breakingbad.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadQuotes
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding

class AdapterExample(
   // private val onItemClick: (characterCard: BreakingBadCharacters) -> Unit
) : RecyclerView.Adapter<AdapterExample.DetailViewHolder>() {

    private var quoteList: List<BreakingBadQuotes> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    private val onClickListener = View.OnClickListener { v ->
//        val card = v?.tag as BreakingBadCharacters
//        onItemClick.invoke(card)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DetailViewHolder(
        binding = CardDetailFragmentBinding.inflate(LayoutInflater.from(parent.context)),
//            onClickListener = onClickListener
    )

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = quoteList[position]
        holder.binding.quoteTV.text = item.author
    }

    override fun getItemCount() = quoteList.size


    class DetailViewHolder(
        val binding: CardDetailFragmentBinding,
//        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root)
//    {
//            init {
//                binding.root.setOnClickListener(onClickListener)
//            }
//        }
}
package com.ntsan.breakingbad.ui.fragments.episode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadEpisodes
import com.ntsan.breakingbad.databinding.EpisodeItemBinding

class EpisodeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var episodesList: List<BreakingBadEpisodes> = emptyList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodeViewHolder(
            binding = EpisodeItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EpisodeViewHolder -> {
                val item = episodesList[position]
                holder.binding.pilotTV.text = item.title
                holder.binding.episodeCountTV.text = item.episode
            }
        }
    }

    override fun getItemCount() = episodesList.size

    class EpisodeViewHolder(
        val binding: EpisodeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
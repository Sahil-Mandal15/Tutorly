package com.sahilm.tutorly.ui.home.screen.shorts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sahilm.tutorly.databinding.ItemShortsBinding
import com.sahilm.tutorly.domain.model.ShortVideoDomainModel

class ShortsAdapter : ListAdapter<ShortVideoDomainModel, ShortsAdapter.ShortsViewHolder>(ShortVideoItemCallback) {

    private var exoPlayer: ExoPlayer? = null
    private var currentPlayerPosition = -1
    private var currentPlayerViewHolder: ShortsViewHolder? = null

    fun setExoPlayer(player: ExoPlayer) {
        this.exoPlayer = player
    }

    fun playVideoAtPosition(position: Int) {
        if (position < 0 || position >= itemCount) return

        exoPlayer?.let { player ->
            if (currentPlayerPosition != position) {
                // Detach player from old position first
                val previousPosition = currentPlayerPosition

                // Set new position
                currentPlayerPosition = position
                val item = getItem(position)
                val mediaItem = MediaItem.fromUri(item.videoUrl)
                player.setMediaItem(mediaItem)
                player.prepare()
                player.play()

                // Rebind both old and new positions to ensure proper attachment/detachment
                if (previousPosition >= 0 && previousPosition < itemCount) {
                    notifyItemChanged(previousPosition)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
        val binding = ItemShortsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ShortsViewHolder(private val binding: ItemShortsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(short: ShortVideoDomainModel, position: Int) {
            binding.apply {
                tvTitle.text = short.title
                
                // Always detach first
                val shouldAttachPlayer = (position == currentPlayerPosition || (currentPlayerPosition < 0 && position == 0))
                
                if (shouldAttachPlayer) {
                    // Attach player only if this is the current position
                    exoPlayer?.let { player ->
                        playerView.player = player
                        currentPlayerViewHolder = this@ShortsViewHolder
                    }
                } else {
                    // Detach player from non-active positions
                    playerView.player = null
                }

                // Add click listener to toggle play/pause
                playerView.setOnClickListener {
                    exoPlayer?.let { player ->
                        if (player.isPlaying) {
                            player.pause()
                        } else {
                            player.play()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private object ShortVideoItemCallback : DiffUtil.ItemCallback<ShortVideoDomainModel>() {
            override fun areItemsTheSame(
                oldItem: ShortVideoDomainModel,
                newItem: ShortVideoDomainModel
            ): Boolean {
                return oldItem.videoId == newItem.videoId
            }

            override fun areContentsTheSame(
                oldItem: ShortVideoDomainModel,
                newItem: ShortVideoDomainModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

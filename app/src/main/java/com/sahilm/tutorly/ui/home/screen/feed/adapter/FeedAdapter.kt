package com.sahilm.tutorly.ui.home.screen.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.ItemFeedHeaderBinding
import com.sahilm.tutorly.databinding.ItemFeedVideoBinding

class FeedAdapter(
    private val clickListener: FeedItemClickListener
): ListAdapter<DataModel, RecyclerView.ViewHolder>(FeedDiffCallback) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_FEED = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataModel.FeedSection -> VIEW_TYPE_FEED
            is DataModel.HeaderSection -> VIEW_TYPE_HEADER
        }
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(p0.context)

        return when(p1) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemFeedHeaderBinding.inflate(inflater, p0, false)
                HeaderViewHolder(binding, clickListener)
            }
            VIEW_TYPE_FEED -> {
                val binding = ItemFeedVideoBinding.inflate(inflater, p0, false)
                FeedViewHolder(binding, clickListener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        p0: RecyclerView.ViewHolder,
        p1: Int
    ) {
        val item = getItem(p1)
        when(p0) {
            is HeaderViewHolder -> p0.bind(item as DataModel.HeaderSection)
            is FeedViewHolder -> p0.bind(item as DataModel.FeedSection)
        }
    }

    class HeaderViewHolder(
        private val binding: ItemFeedHeaderBinding,
        private val clickListener: FeedItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.HeaderSection) {
            binding.tvUserName.text = item.userName
            Glide.with(itemView.context)
                .load(item.userProfilePicture)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivUserProfilePicture)

            binding.ibMoreBtn.setOnClickListener {
                clickListener.onMoreBtnClicked()
            }
        }
    }

    class FeedViewHolder(
        private val binding: ItemFeedVideoBinding,
        private val clickListener: FeedItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.FeedSection) {
            binding.tvTitle.text = item.title
            binding.tvDuration.text = item.duration
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .error(R.drawable.sample_video_thumbnail)
                .into(binding.ivVideoThumbnail)

            binding.cvVideoItem.setOnClickListener {
                clickListener.onFeedItemClicked(
                    item,
                    bindingAdapterPosition
                )
            }
        }
    }
}

object FeedDiffCallback: DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return when {
            oldItem is DataModel.HeaderSection && newItem is DataModel.HeaderSection -> {
                oldItem.userName == newItem.userName
            }
            oldItem is DataModel.FeedSection && newItem is DataModel.FeedSection -> {
                oldItem.videoUrl == newItem.videoUrl
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem == newItem
    }
}

interface FeedItemClickListener {
    fun onMoreBtnClicked()
    fun onFeedItemClicked(item: DataModel.FeedSection, position: Int)
}
package com.sahilm.tutorly.ui.home.screen.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahilm.tutorly.databinding.FragmentFeedBinding
import com.sahilm.tutorly.ui.home.screen.feed.adapter.DataModel
import com.sahilm.tutorly.ui.home.screen.feed.adapter.FeedAdapter
import com.sahilm.tutorly.ui.home.screen.feed.adapter.FeedItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment(), FeedItemClickListener {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWindowInsets()
        setupRecyclerView()
        loadFeedData()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvFeed) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                0
            )
            insets
        }
    }

    private fun setupRecyclerView() {
        feedAdapter = FeedAdapter(this)
        binding.rvFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }
    }

    private fun loadFeedData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.feedDataFlow.collectLatest { feedList ->
                feedAdapter.submitList(feedList)
            }
        }
    }

    override fun onMoreBtnClicked() {
        // Handle more button click
    }

    override fun onFeedItemClicked(item: DataModel.FeedSection, position: Int) {
        // Handle feed item click
        (requireActivity() as? FeedPlayerListener)?.onPlayVideo(item.videoUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface FeedPlayerListener {
    fun onPlayVideo(videoUrl: String)
}
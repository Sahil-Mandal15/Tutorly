package com.sahilm.tutorly.ui.home.screen.shorts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sahilm.tutorly.databinding.FragmentShortsBinding
import com.sahilm.tutorly.ui.home.screen.shorts.adapter.ShortsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShortsFragment : Fragment() {

    private val viewModel: ShortsViewModel by viewModels()
    private var _binding: FragmentShortsBinding? = null
    private val binding get() = _binding!!

    private val shortsAdapter = ShortsAdapter()
    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShortsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupExoPlayer()
        setupRecyclerView()
        observeViewState()
    }

    private fun setupExoPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build().apply {
            shortsAdapter.setExoPlayer(this)
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvShorts.apply {
            adapter = this@ShortsFragment.shortsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val visiblePosition = getVisibleItemPosition()
                        if (visiblePosition >= 0) {
                            // Post to ensure layout is complete and view is bound
                            post {
                                shortsAdapter.playVideoAtPosition(visiblePosition)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun getVisibleItemPosition(): Int {
        val layoutManager = binding.rvShorts.layoutManager as? LinearLayoutManager
        // Use findFirstVisibleItemPosition instead of findFirstCompletelyVisibleItemPosition
        // for more reliable positioning, especially on initial load
        return layoutManager?.findFirstVisibleItemPosition() ?: -1
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    shortsAdapter.submitList(state.shorts) {
                        // Auto-play the first video when data is loaded
                        if (state.shorts.isNotEmpty()) {
                            // Small delay to ensure layout is complete
                            binding.rvShorts.post {
                                shortsAdapter.playVideoAtPosition(0)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // Resume playback of the currently visible video
        exoPlayer?.play()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        exoPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
        _binding = null
    }

    companion object {
        fun newInstance() = ShortsFragment()
    }
}
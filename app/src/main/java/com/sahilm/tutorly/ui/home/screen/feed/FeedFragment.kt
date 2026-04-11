package com.sahilm.tutorly.ui.home.screen.feed

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.FragmentFeedBinding
import com.sahilm.tutorly.domain.repository.UserRepository
import com.sahilm.tutorly.ui.home.screen.feed.adapter.DataModel
import com.sahilm.tutorly.ui.home.screen.feed.adapter.FeedAdapter
import com.sahilm.tutorly.ui.home.screen.feed.adapter.FeedItemClickListener
import com.sahilm.tutorly.ui.home.screen.feed.models.SignOutIntent
import com.sahilm.tutorly.ui.login.activity.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        // Get the more button view from the header
        val headerView = binding.rvFeed.findViewHolderForAdapterPosition(0)?.itemView
        val moreButton = headerView?.findViewById<View>(R.id.ib_more_btn)

        if (moreButton != null) {
            val popupMenu = PopupMenu(requireContext(), moreButton)
            popupMenu.menuInflater.inflate(R.menu.menu_more_options, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_sign_out -> {
                        handleSignOut()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }

    private fun handleSignOut() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.handleUserIntent(SignOutIntent.SignOutUser)

                // Navigate to login screen
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                requireActivity().finish()
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
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
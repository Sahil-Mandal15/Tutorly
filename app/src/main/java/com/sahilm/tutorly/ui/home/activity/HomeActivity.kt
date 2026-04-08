package com.sahilm.tutorly.ui.home.activity

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.NavHostFragment
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.ActivityHomeBinding
import com.sahilm.tutorly.ui.home.screen.feed.FeedPlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), FeedPlayerListener {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragmentContainerView()
    }

    private fun setupFragmentContainerView() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val icHome = binding.layoutBottomBar.findViewById<ImageButton>(R.id.icon_home)
        val icShorts = binding.layoutBottomBar.findViewById<ImageButton>(R.id.icon_shorts)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            icHome.setOnClickListener {
                if (destination.id != R.id.feedFragment) { navController.navigate(R.id.action_shortsFragment_to_feedFragment) }
            }
            icShorts.setOnClickListener {
                if (destination.id != R.id.shortsFragment) { navController.navigate(R.id.action_feedFragment_to_shortsFragment) }
            }
            updateBottomNavIcons(destination.id)
        }
    }

    private fun updateBottomNavIcons(destinationId: Int) {
        val icHome = binding.layoutBottomBar.findViewById<ImageButton>(R.id.icon_home)
        val icShorts = binding.layoutBottomBar.findViewById<ImageButton>(R.id.icon_shorts)

        when (destinationId) {
            R.id.feedFragment -> {
                icHome.setImageResource(R.drawable.ic_home_fill)
                icShorts.setImageResource(R.drawable.ic_shorts_no_fill)
            }
            R.id.shortsFragment -> {
                icHome.setImageResource(R.drawable.ic_home_no_fill)
                icShorts.setImageResource(R.drawable.ic_shorts_fill)

            }
        }
    }

    override fun onPlayVideo(videoUrl: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
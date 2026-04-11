package com.sahilm.tutorly.ui.home.activity

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageButton
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
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

    private var _exoPlayer: ExoPlayer? = null
    private val exoPlayer get() = _exoPlayer!!

    private val isPlayerVisible = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityHomeBinding.inflate(layoutInflater)

        setupFragmentContainerView()
        setupPlayer()

        // Set Compose content
        setContent {
            ActivityContent()
        }
    }

    @Composable
    fun ActivityContent() {
        if (isPlayerVisible.value) {
            ExoPlayerUI()
        } else {
            AndroidView(
                factory = { binding.root },
                modifier = Modifier.fillMaxSize()
            )
        }
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

    private fun setupPlayer() {
        _exoPlayer = ExoPlayer.Builder(this).build()
    }

    override fun onPlayVideo(videoUrl: String) {
        exoPlayer.setMediaItem(
            MediaItem.fromUri(videoUrl)
        )
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        exoPlayer.prepare()
        exoPlayer.play()
        isPlayerVisible.value = true

        // Hide system UI (status bar and navigation bar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private fun closePlayer() {
        isPlayerVisible.value = false
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _exoPlayer = null

        // Show system UI (status bar and navigation bar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            if (controller != null) {
                controller.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            }
        }
    }

    @Composable
    fun ExoPlayerUI() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Handle back press to close player
            LaunchedEffect(Unit) {
                val callback = object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        closePlayer()
                    }
                }
                onBackPressedDispatcher.addCallback(this@HomeActivity, callback)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
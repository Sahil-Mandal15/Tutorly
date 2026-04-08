package com.sahilm.tutorly.ui.home.activity

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
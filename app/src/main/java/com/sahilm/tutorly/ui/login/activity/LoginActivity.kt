package com.sahilm.tutorly.ui.login.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.ActivityHomeBinding
import com.sahilm.tutorly.databinding.ActivityLoginBinding
import com.sahilm.tutorly.ui.home.activity.HomeActivity
import com.sahilm.tutorly.ui.login.models.LoginIntent
import com.sahilm.tutorly.ui.login.models.LoginState
import com.sahilm.tutorly.ui.theme.TutorlyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContent {
            TutorlyTheme { LoginScreen() }
        }

        setupListeners()
        collectStates()
    }

    private fun setupListeners() {
        binding.helloWorld.setOnClickListener {
            viewModel.handleIntent(LoginIntent.GoogleSignIn)
        }
    }

    private fun collectStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Error -> {
                            Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        LoginState.Idle -> {}
                        LoginState.Loading -> {}
                        is LoginState.Success -> navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Composable
    fun LoginScreen() {
        val loginState by viewModel.loginState.collectAsState()
        val userExists by viewModel.userExists.collectAsState()

        val scaleAnimation = remember { Animatable(0.5f) }
        val logoOffsetAnimation = remember { Animatable(0f) }
        val buttonAlphaAnimation = remember { Animatable(0f) }

        val (animationPhase, setAnimationPhase) = remember { mutableStateOf(AnimationPhase.SPLASH) }
        val (showSignInButton, setShowSignInButton) = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            setAnimationPhase(AnimationPhase.SPLASH)

            launch {
                scaleAnimation.animateTo(
                    targetValue = 3.2f,
                    animationSpec = tween(1600)
                )
            }

            launch {
                viewModel.checkUserExists()
            }
        }

        LaunchedEffect(userExists) {
            if (userExists != null) {
                delay(1500)
                if (userExists == true) {
                    navigateToHome()
                } else {
                    setAnimationPhase(AnimationPhase.LOGIN)

                    launch {
                        logoOffsetAnimation.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(800)
                        )
                    }

                    launch {
                        buttonAlphaAnimation.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(800)
                        )
                    }

                    setShowSignInButton(true)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            when (animationPhase) {
                AnimationPhase.SPLASH -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size((80 * scaleAnimation.value).dp)
                            .graphicsLayer(scaleX = 1f, scaleY = 1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.app_name),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size((80 * scaleAnimation.value).dp)
                            .graphicsLayer(scaleX = 1f, scaleY = 1f)
                    )
                }
                AnimationPhase.LOGIN -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size((80 * scaleAnimation.value).dp)
                                .graphicsLayer(
                                    translationY = -logoOffsetAnimation.value
                                )
                        )
                        Image(
                            painter = painterResource(R.drawable.app_name),
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size((80 * scaleAnimation.value).dp)
                                .graphicsLayer(
                                    translationY = -logoOffsetAnimation.value
                                )
                        )
                        Spacer(modifier = Modifier.padding(12.dp))

                        if (showSignInButton) {
                            GoogleSignInButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 32.dp)
                                    .padding(14.dp)
                                    .graphicsLayer(alpha = buttonAlphaAnimation.value),
                                onClick =
                                    {viewModel.handleIntent(LoginIntent.GoogleSignIn)},
                                isLoading = loginState is LoginState.Loading
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GoogleSignInButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        isLoading: Boolean = false
        ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Sign in with Google",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

enum class AnimationPhase {
    SPLASH,
    LOGIN
}
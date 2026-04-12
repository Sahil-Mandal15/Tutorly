package com.sahilm.tutorly.ui.login.activity

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sahilm.tutorly.R
import com.sahilm.tutorly.databinding.ActivityLoginBinding
import com.sahilm.tutorly.ui.home.activity.HomeActivity
import com.sahilm.tutorly.ui.login.models.LoginIntent
import com.sahilm.tutorly.ui.login.models.LoginState
import com.sahilm.tutorly.ui.theme.TutorlyTheme
import com.sahilm.tutorly.ui.utils.AnimationConstants
import com.sahilm.tutorly.ui.utils.UIConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        collectStates()
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

        val scaleAnimation = remember { Animatable(AnimationConstants.LOGIN_SPLASH_INITIAL_SCALE) }
        val logoOffsetAnimation = remember { Animatable(AnimationConstants.LOGIN_LOGO_INITIAL_OFFSET) }
        val buttonAlphaAnimation = remember { Animatable(AnimationConstants.LOGIN_BUTTON_INITIAL_ALPHA) }

        val (animationPhase, setAnimationPhase) = remember { mutableStateOf(AnimationPhase.SPLASH) }
        val (showSignInButton, setShowSignInButton) = remember { mutableStateOf(false) }

        LaunchedEffect(Unit)  {
            setAnimationPhase(AnimationPhase.SPLASH)

            launch {
                scaleAnimation.animateTo(
                    targetValue = AnimationConstants.LOGIN_SPLASH_TARGET_SCALE,
                    animationSpec = tween(AnimationConstants.LOGIN_SPLASH_ANIMATION_DURATION_MS)
                )
            }

            launch {
                viewModel.checkUserExists()
            }
        }

        LaunchedEffect(userExists) {
            if (userExists != null) {
                delay(AnimationConstants.LOGIN_SPLASH_TO_LOGIN_DELAY_MS.toLong())
                if (userExists == true) {
                    navigateToHome()
                } else {
                    setAnimationPhase(AnimationPhase.LOGIN)

                    launch {
                        logoOffsetAnimation.animateTo(
                            targetValue = AnimationConstants.LOGIN_LOGO_ANIMATION_TARGET,
                            animationSpec = tween(AnimationConstants.LOGIN_LOGO_ANIMATION_DURATION_MS)
                        )
                    }

                    launch {
                        buttonAlphaAnimation.animateTo(
                            targetValue = AnimationConstants.LOGIN_LOGO_ANIMATION_TARGET,
                            animationSpec = tween(AnimationConstants.LOGIN_BUTTON_ALPHA_ANIMATION_DURATION_MS)
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
                            .size((UIConstants.LOGIN_LOGO_SIZE_DP * scaleAnimation.value).dp)
                            .graphicsLayer(scaleX = AnimationConstants.LOGIN_GRAPHICS_LAYER_SCALE, scaleY = AnimationConstants.LOGIN_GRAPHICS_LAYER_SCALE)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.app_name),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size((UIConstants.LOGIN_LOGO_SIZE_DP * scaleAnimation.value).dp)
                            .graphicsLayer(scaleX = AnimationConstants.LOGIN_GRAPHICS_LAYER_SCALE, scaleY = AnimationConstants.LOGIN_GRAPHICS_LAYER_SCALE)
                    )
                }
                AnimationPhase.LOGIN -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(UIConstants.LOGIN_CONTENT_PADDING_DP.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(UIConstants.LOGIN_TOP_SPACER_HEIGHT_DP.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size((UIConstants.LOGIN_LOGO_SIZE_DP * scaleAnimation.value).dp)
                                .graphicsLayer(
                                    translationY = -logoOffsetAnimation.value
                                )
                        )
                        Image(
                            painter = painterResource(R.drawable.app_name),
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size((UIConstants.LOGIN_LOGO_SIZE_DP * scaleAnimation.value).dp)
                                .graphicsLayer(
                                    translationY = -logoOffsetAnimation.value
                                )
                        )
                        Spacer(modifier = Modifier.padding(UIConstants.LOGIN_LOGO_SPACING_DP.dp))

                        if (showSignInButton) {
                            GoogleSignInButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = UIConstants.LOGIN_BUTTON_BOTTOM_PADDING_DP.dp)
                                    .padding(UIConstants.LOGIN_BUTTON_HORIZONTAL_PADDING_DP.dp)
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
                .height(UIConstants.LOGIN_BUTTON_HEIGHT_DP.dp)
                .clip(RoundedCornerShape(UIConstants.LOGIN_BUTTON_CORNER_RADIUS_DP.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(UIConstants.LOGIN_PROGRESS_INDICATOR_SIZE_DP.dp)
                )
            } else {
                Text(
                    text = "Sign in with Google",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = UIConstants.LOGIN_BUTTON_TEXT_FONT_SIZE_SP.sp,
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
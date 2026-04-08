package com.sahilm.tutorly.ui.login.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sahilm.tutorly.core.utils.ResultState
import com.sahilm.tutorly.domain.model.UserData
import com.sahilm.tutorly.domain.repository.UserRepository
import com.sahilm.tutorly.ui.helpers.GoogleAuthUiHelper
import com.sahilm.tutorly.ui.login.models.LoginIntent
import com.sahilm.tutorly.ui.login.models.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleAuthUiHelper: GoogleAuthUiHelper,
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _userExists = MutableStateFlow<Boolean?>(null)
    val userExists: StateFlow<Boolean?> = _userExists

    fun checkUserExists() {
        viewModelScope.launch {
            val currentUser = firebaseAuth.currentUser
            _userExists.value = currentUser != null
        }
    }

    fun handleIntent(intent: LoginIntent) {
        when(intent) {
            is LoginIntent.GoogleSignIn -> performGoogleSignIn()
        }
    }

    private fun performGoogleSignIn() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = googleAuthUiHelper.signInWithGoogle()

            _loginState.value = when(result) {
                is ResultState.Error -> LoginState.Error(result.message)
                ResultState.Loading -> LoginState.Loading
                is ResultState.Success<UserData> -> {
                    storeUserData(result.data)
                    LoginState.Success(result.data)
                }
            }
        }
    }

    private fun storeUserData(userData: UserData) {
        viewModelScope.launch {
            userRepository.saveUserData(
                userId = userData.userId,
                userName = userData.userName,
                userProfilePic = userData.profilePictureUrl
            )
        }
    }
}
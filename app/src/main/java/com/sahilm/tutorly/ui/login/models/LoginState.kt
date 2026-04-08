package com.sahilm.tutorly.ui.login.models

import com.sahilm.tutorly.domain.model.UserData

sealed class LoginState {
    data object Idle: LoginState()
    data object Loading: LoginState()
    data class Success(val userData: UserData) : LoginState()
    data class Error(val message: String): LoginState()
}

sealed class LoginIntent {
    data object GoogleSignIn: LoginIntent()
}
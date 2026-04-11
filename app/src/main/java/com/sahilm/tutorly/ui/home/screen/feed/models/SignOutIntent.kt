package com.sahilm.tutorly.ui.home.screen.feed.models

sealed class SignOutIntent {
    data object SignOutUser: SignOutIntent()
}
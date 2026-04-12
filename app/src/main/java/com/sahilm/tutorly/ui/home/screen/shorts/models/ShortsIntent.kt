package com.sahilm.tutorly.ui.home.screen.shorts.models

sealed class ShortsIntent {
    data object LoadShorts : ShortsIntent()
    data class PlayVideo(val videoId: String) : ShortsIntent()
    data class PauseVideo(val videoId: String) : ShortsIntent()
}


package com.sahilm.tutorly.ui.home.screen.shorts.models

import com.sahilm.tutorly.domain.model.ShortVideoDomainModel

data class ShortsViewState(
    val isLoading: Boolean = false,
    val shorts: List<ShortVideoDomainModel> = emptyList(),
    val error: String? = null,
    val currentPlayingVideoId: String? = null,
)


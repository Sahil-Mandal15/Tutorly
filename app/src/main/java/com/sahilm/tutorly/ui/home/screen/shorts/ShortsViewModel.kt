package com.sahilm.tutorly.ui.home.screen.shorts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahilm.tutorly.domain.repository.ShortsRepository
import com.sahilm.tutorly.ui.home.screen.shorts.models.ShortsIntent
import com.sahilm.tutorly.ui.home.screen.shorts.models.ShortsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortsViewModel @Inject constructor(
    private val shortsRepository: ShortsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(ShortsViewState())
    val viewState: StateFlow<ShortsViewState> = _viewState.asStateFlow()

    init {
        handleIntent(ShortsIntent.LoadShorts)
    }

    fun handleIntent(intent: ShortsIntent) {
        when (intent) {
            is ShortsIntent.LoadShorts -> loadShorts()
            is ShortsIntent.PlayVideo -> playVideo(intent.videoId)
            is ShortsIntent.PauseVideo -> pauseVideo(intent.videoId)
        }
    }

    private fun loadShorts() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            try {
                val shorts = shortsRepository.getShorts()
                _viewState.update {
                    it.copy(isLoading = false, shorts = shorts)
                }
            } catch (e: Exception) {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun playVideo(videoId: String) {
        _viewState.update { it.copy(currentPlayingVideoId = videoId) }
    }

    private fun pauseVideo(videoId: String) {
        _viewState.update {
            if (it.currentPlayingVideoId == videoId) {
                it.copy(currentPlayingVideoId = null)
            } else {
                it
            }
        }
    }
}


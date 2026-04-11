package com.sahilm.tutorly.ui.home.screen.feed

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sahilm.tutorly.domain.repository.FeedRepository
import com.sahilm.tutorly.domain.repository.UserRepository
import com.sahilm.tutorly.ui.helpers.GoogleAuthUiHelper
import com.sahilm.tutorly.ui.home.screen.feed.adapter.DataModel
import com.sahilm.tutorly.ui.home.screen.feed.models.SignOutIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    @Suppress("UNUSED_PARAMETER")
    private val userRepository: UserRepository,
    private val googleAuthUiHelper: GoogleAuthUiHelper
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val feedDataFlow: Flow<List<DataModel>> = userRepository.getUserData()
        .flatMapLatest { userData ->
            flow {
                val feedList = mutableListOf<DataModel>()

                // Add header section with user data
                val userProfilePic = userData?.profilePictureUrl
                    ?: "android.resource://com.sahilm.tutorly/drawable/ic_launcher_foreground"
                val userName = userData?.userName ?: "Welcome Back"

                feedList.add(
                    DataModel.HeaderSection(
                        userProfilePicture = userProfilePic,
                        userName = userName
                    )
                )

                // Add feed items from mock data
                val videoList = feedRepository.provideMockFeedVideoData()
                videoList.forEach { video ->
                    feedList.add(
                        DataModel.FeedSection(
                            thumbnail = video.thumbnail,
                            duration = video.duration,
                            title = video.title,
                            videoUrl = video.videoUrl
                        )
                    )
                }

                emit(feedList)
            }
        }

    fun handleUserIntent(intent: SignOutIntent) {
        when(intent) {
            is SignOutIntent.SignOutUser -> signOutUser()
        }
    }

    private fun signOutUser() {
        viewModelScope.launch {
            userRepository.signOutUser()
            googleAuthUiHelper.signOutUser()
        }

    }
}

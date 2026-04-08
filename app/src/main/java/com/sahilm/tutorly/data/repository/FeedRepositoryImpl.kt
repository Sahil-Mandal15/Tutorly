package com.sahilm.tutorly.data.repository

import com.sahilm.tutorly.domain.model.VideoDomainModel
import com.sahilm.tutorly.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor() : FeedRepository {

    override fun provideMockFeedVideoData(): List<VideoDomainModel> {
        // Using local drawable resources that are reliable and work offline
        val thumbnailUri = "android.resource://com.sahilm.tutorly/drawable/sample_video_thumbnail"
        val localVideoUri = "android.resource://com.sahilm.tutorly/raw/sample_video"

        return listOf(
            VideoDomainModel(
                videoId = "1",
                videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
                thumbnail = thumbnailUri,
                duration = "5:30",
                title = "Introduction to Kotlin"
            ),
            VideoDomainModel(
                videoId = "2",
                videoUrl = localVideoUri,
                thumbnail = thumbnailUri,
                duration = "8:15",
                title = "Android Development Basics"
            ),
            VideoDomainModel(
                videoId = "3",
                videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
                thumbnail = thumbnailUri,
                duration = "6:45",
                title = "Building UI with Jetpack Compose"
            ),
            VideoDomainModel(
                videoId = "4",
                videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
                thumbnail = thumbnailUri,
                duration = "7:20",
                title = "Understanding Coroutines"
            ),
            VideoDomainModel(
                videoId = "5",
                videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
                thumbnail = thumbnailUri,
                duration = "9:00",
                title = "Database Design with Room"
            ),
            VideoDomainModel(
                videoId = "6",
                videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
                thumbnail = thumbnailUri,
                duration = "4:50",
                title = "API Integration Best Practices"
            )
        )
    }
}

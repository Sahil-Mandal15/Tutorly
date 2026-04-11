package com.sahilm.tutorly.data.repository

import com.sahilm.tutorly.domain.model.VideoDomainModel
import com.sahilm.tutorly.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor() : FeedRepository {

    override fun provideMockFeedVideoData(): List<VideoDomainModel> {
        return listOf(
            VideoDomainModel(
                videoId = "1",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_activity_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_activity",
                duration = "12m 47s",
                title = "Activities & the Activity Lifecycle - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "2",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_tasks_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_backstack",
                duration = "7m 11s",
                title = "Tasks, Back Stack & Launch Modes - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "3",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_viewmodel_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_viewmodels",
                duration = "18m 46s",
                title = "ViewModels & Configuration Changes - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "4",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_context_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_context",
                duration = "11m 22s",
                title = "What is the Context? - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "5",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_resource_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_resources",
                duration = "16m 14s",
                title = "Resources & Qualifiers - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "6",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_intent_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_intents",
                duration = "25m 36s",
                title = "Intents & Intent Filters - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "7",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_broadcast_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_broadcast",
                duration = "11m 33s",
                title = "Broadcasts & Broadcast Receivers - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "8",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_foreground_services_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_foreground_services",
                duration = "22m 22s",
                title = "Foreground Services - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "9",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_workmanager_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_workmanager",
                duration = "34m 22s",
                title = "WorkManager - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "10",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/android_basics_uri_videos",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/android_basics_uri",
                duration = "14m 21s",
                title = "Uris (Unique Resource Identifier) - Android Basics 2023"
            )
        )
    }
}

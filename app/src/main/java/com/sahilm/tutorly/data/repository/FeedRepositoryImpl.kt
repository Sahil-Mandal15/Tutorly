package com.sahilm.tutorly.data.repository

import com.sahilm.tutorly.domain.model.VideoDomainModel
import com.sahilm.tutorly.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor() : FeedRepository {

    override fun provideMockFeedVideoData(): List<VideoDomainModel> {
        return listOf(
            VideoDomainModel(
                videoId = "1",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/activity_lifecycle",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/activity_thumbnail",
                duration = "12m 47s",
                title = "Activities & the Activity Lifecycle - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "2",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/tasks",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/tasks_thumbnail",
                duration = "7m 11s",
                title = "Tasks, Back Stack & Launch Modes - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "3",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/viewmodels",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/viewmodel_thumbnail",
                duration = "18m 46s",
                title = "ViewModels & Configuration Changes - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "4",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/context",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/context_thumbnail",
                duration = "11m 22s",
                title = "What is the Context? - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "5",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/resources",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/resources_thumbnail",
                duration = "16m 14s",
                title = "Resources & Qualifiers - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "6",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/intents",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/intents_thumbnail",
                duration = "25m 36s",
                title = "Intents & Intent Filters - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "7",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/broadcast",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/broadcasts_thumbnail",
                duration = "11m 33s",
                title = "Broadcasts & Broadcast Receivers - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "8",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/foreground_services",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/foreground_services_thumbnail",
                duration = "22m 22s",
                title = "Foreground Services - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "9",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/workmanager",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/workmanager_thumbnail",
                duration = "34m 22s",
                title = "WorkManager - Android Basics 2023"
            ),
            VideoDomainModel(
                videoId = "10",
                videoUrl = "android.resource://com.sahilm.tutorly/raw/uri",
                thumbnail = "android.resource://com.sahilm.tutorly/raw/uri_thumbnail",
                duration = "14m 21s",
                title = "Uris (Unique Resource Identifier) - Android Basics 2023"
            )
        )
    }
}

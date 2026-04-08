package com.sahilm.tutorly.ui.home.screen.feed.adapter

sealed class DataModel {
    data class HeaderSection(
        val userProfilePicture: String,
        val userName: String,
    ): DataModel()

    data class FeedSection(
        val thumbnail: String,
        val duration: String,
        val title: String,
        val videoUrl: String
    ): DataModel()
}

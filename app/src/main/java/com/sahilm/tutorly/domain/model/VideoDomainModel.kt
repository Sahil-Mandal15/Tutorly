package com.sahilm.tutorly.domain.model

data class VideoDomainModel(
    val videoId: String,
    val videoUrl: String,
    val thumbnail: String,
    val duration: String,
    val title: String,
)

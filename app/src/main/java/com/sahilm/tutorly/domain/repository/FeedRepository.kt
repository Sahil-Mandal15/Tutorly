package com.sahilm.tutorly.domain.repository

import com.sahilm.tutorly.domain.model.VideoDomainModel

interface FeedRepository {
    fun provideMockFeedVideoData(): List<VideoDomainModel>
}
package com.sahilm.tutorly.domain.repository

import com.sahilm.tutorly.domain.model.ShortVideoDomainModel

interface ShortsRepository {
    fun getShorts(): List<ShortVideoDomainModel>
}


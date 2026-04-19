package com.sahilm.tutorly.data.repository

import com.sahilm.tutorly.domain.model.ShortVideoDomainModel
import com.sahilm.tutorly.domain.repository.ShortsRepository
import javax.inject.Inject

class ShortsRepositoryImpl @Inject constructor() : ShortsRepository {

    override fun getShorts(): List<ShortVideoDomainModel> {
        val shorts = mutableListOf<ShortVideoDomainModel>()
        repeat(15) { index ->
            when (index % 3) {
                0 -> shorts.add(
                    ShortVideoDomainModel(
                        videoId = "${index + 1}",
                        videoUrl = "android.resource://com.sahilm.tutorly/raw/clean_architecure_shorts",
                        title = "Clean Architecture Explained (Part ${(index / 3) + 1})"
                    )
                )
                1 -> shorts.add(
                    ShortVideoDomainModel(
                        videoId = "${index + 1}",
                        videoUrl = "android.resource://com.sahilm.tutorly/raw/android_interview_shorts",
                        title = "Interview Tips & Tricks (Part ${(index / 3) + 1})"
                    )
                )
                2 -> shorts.add(
                    ShortVideoDomainModel(
                        videoId = "${index + 1}",
                        videoUrl = "android.resource://com.sahilm.tutorly/raw/mvvm_vs_mvi_shorts",
                        title = "MVVM vs MVI Architecture (Part ${(index / 3) + 1})"
                    )
                )
            }
        }
        return shorts
    }
}


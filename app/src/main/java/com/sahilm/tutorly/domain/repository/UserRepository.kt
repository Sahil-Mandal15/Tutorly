package com.sahilm.tutorly.domain.repository

import com.sahilm.tutorly.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserData(
        userId: String,
        userName: String?,
        userProfilePic: String?,
    )

    fun getUserData(): Flow<UserData?>

    suspend fun clearUserData()
}
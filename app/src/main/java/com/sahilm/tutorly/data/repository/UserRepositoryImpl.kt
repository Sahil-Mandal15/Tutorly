package com.sahilm.tutorly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sahilm.tutorly.core.utils.Constants.Companion.USER_ID_DATA_STORE_KEY
import com.sahilm.tutorly.core.utils.Constants.Companion.USER_NAME_DATA_STORE_KEY
import com.sahilm.tutorly.core.utils.Constants.Companion.USER_PROFILE_PICTURE_DATA_STORE_KEY
import com.sahilm.tutorly.domain.model.UserData
import com.sahilm.tutorly.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): UserRepository {
    override suspend fun saveUserData(
        userId: String,
        userName: String?,
        userProfilePic: String?
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID_DATA_STORE_KEY] = userId
            preferences[USER_NAME_DATA_STORE_KEY] = userName ?: "User"
            preferences[USER_PROFILE_PICTURE_DATA_STORE_KEY] = userProfilePic ?: ""
        }
    }

    override fun getUserData(): Flow<UserData?> = dataStore.data.map { preferences ->
        UserData(
            userId = preferences[USER_ID_DATA_STORE_KEY] ?: return@map null,
            userName = preferences[USER_NAME_DATA_STORE_KEY],
            profilePictureUrl = preferences[USER_PROFILE_PICTURE_DATA_STORE_KEY]
        )
    }

    override suspend fun clearUserData() {
        dataStore.edit { it.clear() }
    }
}
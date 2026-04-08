package com.sahilm.tutorly.core.utils

import androidx.datastore.preferences.core.stringPreferencesKey

class Constants {
    companion object {
        const val PREFERENCE_NAME = "tutorly_preference"
        val USER_ID_DATA_STORE_KEY = stringPreferencesKey("user_id")
        val USER_NAME_DATA_STORE_KEY = stringPreferencesKey("user_name")
        val USER_PROFILE_PICTURE_DATA_STORE_KEY = stringPreferencesKey("user_profile_picture")
    }
}
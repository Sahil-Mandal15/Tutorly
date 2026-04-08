package com.sahilm.tutorly.core.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.sahilm.tutorly.ui.helpers.GoogleAuthUiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleAuthUiHelper(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): GoogleAuthUiHelper {
        return GoogleAuthUiHelper(context, firebaseAuth)
    }
}


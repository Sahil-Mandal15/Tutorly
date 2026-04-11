package com.sahilm.tutorly.core.di

import com.sahilm.tutorly.data.repository.FeedRepositoryImpl
import com.sahilm.tutorly.data.repository.UserRepositoryImpl
import com.sahilm.tutorly.domain.repository.FeedRepository
import com.sahilm.tutorly.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        feedRepositoryImpl: FeedRepositoryImpl
    ): FeedRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}




package com.dungnm.example.compose.home.di

import android.content.Context
import com.dungnm.example.compose.home.repo.IGithubRepo
import com.dungnm.example.compose.home.repo.MockGithubRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DevRepositoryModule {

    @Provides
    fun provideGithubRepo(@ApplicationContext context: Context): IGithubRepo {
        return MockGithubRepo(context)
    }
}
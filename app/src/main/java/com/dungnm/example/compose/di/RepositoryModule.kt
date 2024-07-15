package com.dungnm.example.compose.di

import android.content.Context
import com.dungnm.example.compose.BuildConfig
import com.dungnm.example.compose.network.service.GithubService
import com.dungnm.example.compose.network.repo.github.GithubRepo
import com.dungnm.example.compose.network.repo.github.IGithubRepo
import com.dungnm.example.compose.network.repo.github.MockGithubRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepo(
        @ApplicationContext context: Context, githubService: GithubService
    ): IGithubRepo {
        return if (BuildConfig.MOCK_ENABLE) {
            MockGithubRepo(context)
        } else {
            GithubRepo(githubService)
        }
    }
}
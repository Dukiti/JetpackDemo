package com.dungnm.example.compose.di

import com.dungnm.example.compose.network.service.GithubService
import com.dungnm.example.compose.network.repo.GithubRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepo(githubService: GithubService): GithubRepo {
        return GithubRepo(githubService)
    }
}
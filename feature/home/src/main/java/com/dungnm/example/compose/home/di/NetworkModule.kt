package com.dungnm.example.compose.home.di

import com.dungnm.example.compose.home.network.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGithubComposeService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}
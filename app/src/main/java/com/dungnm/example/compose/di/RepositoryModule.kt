package com.dungnm.example.compose.di

import android.content.Context
import com.dungnm.example.compose.BuildConfig
import com.dungnm.example.compose.network.repo.github.GithubRepo
import com.dungnm.example.compose.network.repo.IGithubRepo
import com.dungnm.example.compose.network.repo.github.MockGithubRepo
import com.dungnm.example.compose.network.repo.ILoginRepo
import com.dungnm.example.compose.network.repo.login.LoginRepo
import com.dungnm.example.compose.network.repo.login.MockLoginRepo
import com.dungnm.example.compose.network.service.GithubService
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

    @Provides
    @Singleton
    fun provideLoginRepo(
        @ApplicationContext context: Context, githubService: GithubService
    ): ILoginRepo {
        return if (BuildConfig.MOCK_ENABLE) {
            MockLoginRepo()
        } else {
            LoginRepo()
        }
    }
}
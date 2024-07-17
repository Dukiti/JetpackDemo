package com.dungnm.example.compose.di

import com.dungnm.example.compose.network.repo.IGithubRepo
import com.dungnm.example.compose.network.repo.ILoginRepo
import com.dungnm.example.compose.network.repo.github.GithubRepo
import com.dungnm.example.compose.network.repo.login.LoginRepo
import com.dungnm.example.compose.network.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGithubRepo(
        githubService: GithubService
    ): IGithubRepo {
        return GithubRepo(githubService)
    }

    @Provides
    fun provideLoginRepo(): ILoginRepo {
        return LoginRepo()
    }
}
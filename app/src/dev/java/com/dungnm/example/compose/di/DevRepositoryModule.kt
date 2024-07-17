package com.dungnm.example.compose.di

import android.content.Context
import com.dungnm.example.compose.network.repo.IGithubRepo
import com.dungnm.example.compose.network.repo.ILoginRepo
import com.dungnm.example.compose.network.repo.github.MockGithubRepo
import com.dungnm.example.compose.network.repo.login.MockLoginRepo
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

    @Provides
    fun provideLoginRepo(): ILoginRepo {
        return MockLoginRepo()
    }
}
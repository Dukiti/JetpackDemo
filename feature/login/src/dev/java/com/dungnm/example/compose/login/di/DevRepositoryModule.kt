package com.dungnm.example.compose.login.di

import com.dungnm.example.compose.login.repo.ILoginRepo
import com.dungnm.example.compose.login.repo.MockLoginRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DevRepositoryModule {

    @Provides
    fun provideLoginRepo(): ILoginRepo {
        return MockLoginRepo()
    }
}
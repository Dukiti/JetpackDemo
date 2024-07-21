package com.dungnm.example.compose.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.dungnm.example.compose.network.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavModule {

//    @Provides
//    @Singleton
//    fun provideNavController(@ApplicationContext context: Context) {
//        NavHostController(context).apply {
//            navigatorProvider.addNavigator(ComposeNavigator())
//            navigatorProvider.addNavigator(DialogNavigator())
//        }
//    }
}